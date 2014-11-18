package com.crazy.contentdoesnotmatter.activities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.crazy.contentdoesnotmatter.R;
import com.crazy.contentdoesnotmatter.views.ImageEditView;

public class PhotoEditActivity extends Activity {

	private String firstImage;
	private String secondImage;

	private Bitmap firstImageBitmap;
	private Bitmap secondImageBitmap;

	public static final String FIRST_IMAGE = "com.crazy.contentdoesnotmatter.FIRST_IMAGE";
	public static final String SECOND_IMAGE = "com.crazy.contentdoesnotmatter.SECOND_IMAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_edit);

		Intent intent = getIntent();
		firstImage = intent.getStringExtra(FIRST_IMAGE);
		secondImage = intent.getStringExtra(SECOND_IMAGE);

		Uri firstImageUri = Uri.parse(firstImage);
		Uri secondImageUri = Uri.parse(secondImage);

		if (firstImageUri.toString().startsWith("http")) {
			new DownloadImageTask(FIRST_IMAGE).execute(firstImage);
		} else {
			try {
				firstImageBitmap = MediaStore.Images.Media.getBitmap(
						getContentResolver(), firstImageUri);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (secondImageUri.toString().startsWith("http")) {
			new DownloadImageTask(SECOND_IMAGE).execute(secondImage);
		} else {
			try {
				secondImageBitmap = MediaStore.Images.Media.getBitmap(
						getContentResolver(), secondImageUri);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		final ImageEditView imgView = (ImageEditView) findViewById(R.id.imageView1);
		imgView.setBitmaps(firstImageBitmap, secondImageBitmap);
		

		SeekBar seekBar = (SeekBar) findViewById(R.id.alphaSeekBar);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				imgView.setAlpaDifference(progress);
			}
		});

	}
	
	public void setFirst(View view) {
		ImageEditView imgView = (ImageEditView) findViewById(R.id.imageView1);
		imgView.setFirst();
	}
	
	public void setSecond(View view) {
		ImageEditView imgView = (ImageEditView) findViewById(R.id.imageView1);
		imgView.setSecond();
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

		private ProgressDialog progressDialog;
		private String imageId;

		public DownloadImageTask(String imageId) {
			this.imageId = imageId;
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(PhotoEditActivity.this, "Loading", "Please wait");
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

			return bmp;
		}

		protected void onPostExecute(Bitmap result) {
			ImageEditView view = (ImageEditView)PhotoEditActivity.this.findViewById(R.id.imageView1);
			if (imageId == FIRST_IMAGE) {
				view.setBitmaps(result, null);
			} else {
				view.setBitmaps(null, result);
			}
			progressDialog.dismiss();
		}
	}

}
