package com.crazy.contentdoesnotmatter;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class InstagramAdapter extends BaseAdapter {

	private String accessToken;
	private ArrayList<String> Objects;
	private Context context;
	private LruCache<String, Bitmap> cache;

	public InstagramAdapter(Context context, String accessToken, LruCache<String, Bitmap> cache) {
		this.accessToken = accessToken;
		this.Objects = new ArrayList<String>();
		this.context = context;
		this.cache = cache;
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
			view = new ImageView(context);
			view.setScaleType(ScaleType.CENTER_CROP);
			Bitmap bmp = cache.get(Objects.get(position));
			if (bmp != null) {
				view.setImageBitmap(bmp);
			} else {
				new DownloadImageTask(view).execute(Objects.get(position));
				view.setImageResource(R.drawable.placeholder);
			}
		}
		return view;
	}

	private class FetchInstagramAnswer extends
			AsyncTask<String, Void, ArrayList<String>> {

		final static private String GET_USER_MEDIA = "https://api.instagram.com/v1/users/self/media/recent";
		private ProgressDialog progressDialog;
		
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(context, "Loading", "Please wait", true, true,
				new DialogInterface.OnCancelListener(){
	                @Override
	                public void onCancel(DialogInterface dialog) {
	                    FetchInstagramAnswer.this.cancel(true);
	                    progressDialog.cancel();
	                }
	            });
		}
		
		protected ArrayList<String> doInBackground(String... accessToken) {
			String url = GET_USER_MEDIA + "?access_token=" + accessToken[0];
			ArrayList<String> result = new ArrayList<String>();
			try {
				InputStream in = new URL(url).openStream();
				String response = utils.StreamToString(in);
				JSONObject jsonObject = new JSONObject(response);
				JSONArray data = jsonObject.getJSONArray("data");

				for (int i = 0; i < data.length(); i++) {
					String imageURL = data.getJSONObject(i)
							.getJSONObject("images").getJSONObject("thumbnail")
							.getString("url");
					result.add(imageURL);
				}
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return result;
		}

		protected void onPostExecute(ArrayList<String> result) {
			for (String url : result) {
				Objects.add(url);
			}
			progressDialog.dismiss();
			notifyDataSetChanged();
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
