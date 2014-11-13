package com.crazy.contentdoesnotmatter;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class InstagramAdapter extends BaseAdapter {

	private String accessToken;
	private ArrayList<String> Objects;

	public InstagramAdapter(String accessToken) {
		this.accessToken = accessToken;
		this.Objects = new ArrayList<String>();
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
			view = new ImageView(parent.getContext());
			new DownloadImageTask(view)
					.execute(Objects.get(position));
			view.setImageResource(R.drawable.ic_launcher);
		}
		return view;
	}

	private class FetchInstagramAnswer extends
			AsyncTask<String, Void, ArrayList<String>> {

		final static private String GET_USER_MEDIA = "https://api.instagram.com/v1/users/self/media/recent";

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
			notifyDataSetChanged();
		}
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}
}
