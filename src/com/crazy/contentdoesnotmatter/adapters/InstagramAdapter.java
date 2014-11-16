package com.crazy.contentdoesnotmatter.adapters;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.crazy.contentdoesnotmatter.R;
import com.crazy.utils.utils;

public class InstagramAdapter extends BaseAdapter {

	private String accessToken;
	private ArrayList<PhotoInfo> Objects;
	private GridView gridView;
	private LruCache<String, Bitmap> cache;
	private String nextUrl;
	private Boolean isLoading;
	
	public class PhotoInfo {
		public String thumnailURL = "";
		public String fullSizeURL = "";
	}

	public InstagramAdapter(GridView gridView, String accessToken, LruCache<String, Bitmap> cache) {
		this.accessToken = accessToken;
		this.Objects = new ArrayList<PhotoInfo>();
		this.gridView = gridView;
		this.cache = cache;
		this.nextUrl = "";
		this.isLoading = false;
		
		gridView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if ((firstVisibleItem + visibleItemCount) >= totalItemCount) {
					if (nextUrl != "" && isLoading == false) {
						isLoading = true;
						new FetchInstagramAnswer().execute(InstagramAdapter.this.accessToken);
					}
				}
			}
		});
		
		new FetchInstagramAnswer().execute(this.accessToken);
	}

	@Override
	public int getCount() {
		return Objects.size();
	}

	@Override
	public Object getItem(int index) {
		return Objects.get(index);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView view = (ImageView) convertView;
		if (view == null) {
			view = new ImageView(gridView.getContext());
		}
		view.setScaleType(ScaleType.CENTER_CROP);
		Bitmap bmp = cache.get(Objects.get(position).thumnailURL);
		if (bmp != null) {
			view.setImageBitmap(bmp);
		} else {
			new DownloadImageTask(view).execute(Objects.get(position).thumnailURL);
			view.setImageResource(R.drawable.placeholder);
		}
		return view;
	}
	

	private class FetchInstagramAnswer extends
			AsyncTask<String, Void, ArrayList<PhotoInfo>> {

		//final static private String GET_USER_MEDIA = "https://api.instagram.com/v1/users/199862995/media/recent";
		final static private String GET_USER_MEDIA = "https://api.instagram.com/v1/users/self/media/recent";
		private ProgressDialog progressDialog;
		
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(gridView.getContext(), "Loading", "Please wait", true, true,
				new DialogInterface.OnCancelListener(){
	                @Override
	                public void onCancel(DialogInterface dialog) {
	                    FetchInstagramAnswer.this.cancel(true);
	                    progressDialog.cancel();
	                }
	            });
		}
		
		protected ArrayList<PhotoInfo> doInBackground(String... accessTokens) {
			String url;
			String accessToken = accessTokens[0];
			if (nextUrl == "") {
				url = GET_USER_MEDIA + "?access_token=" + accessToken;
			} else {
				url = nextUrl;
			}
			ArrayList<PhotoInfo> result = new ArrayList<PhotoInfo>();
			try {
				InputStream in = new URL(url).openStream();
				String response = utils.StreamToString(in);
				JSONObject jsonObject = new JSONObject(response);
				JSONArray data = jsonObject.getJSONArray("data");
				JSONObject pagination = jsonObject.getJSONObject("pagination");

				for (int i = 0; i < data.length(); i++) {
					PhotoInfo photoInfo = new PhotoInfo();
					JSONObject images = data.getJSONObject(i)
							.getJSONObject("images");
					photoInfo.thumnailURL = images.getJSONObject("thumbnail")
							.getString("url");
					photoInfo.fullSizeURL = images.getJSONObject("standard_resolution")
							.getString("url");
					result.add(photoInfo);
				}
				nextUrl = "";
				nextUrl = pagination.getString("next_url");
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return result;
		}

		protected void onPostExecute(ArrayList<PhotoInfo> result) {
			for (PhotoInfo url : result) {
				Objects.add(url);
			}
			progressDialog.dismiss();
			notifyDataSetChanged();
			isLoading = false;
		}
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView imgView;

		public DownloadImageTask(ImageView bmImage) {
			this.imgView = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap bmp = null;
			try {
				InputStream in = new URL(urldisplay).openStream();
				bmp = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			
			if (bmp != null) {
				cache.put(urldisplay, bmp);
			}
			return bmp;
		}

		protected void onPostExecute(Bitmap result) {
			imgView.setImageBitmap(result);
		}
	}
}
