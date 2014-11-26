package com.crazy.contentdoesnotmatter.fragments;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.crazy.contentdoesnotmatter.R;
import com.crazy.contentdoesnotmatter.fragments.SelectorFragment.ResultReturner;
import com.crazy.contentdoesnotmatter.views.ImageEditView;

public class PhotoCropFragment extends Fragment implements OnClickListener, ResultReturner {

	private String firstImage;
	private String secondImage;

	private Bitmap firstImageBitmap;
	private Bitmap secondImageBitmap;

	public static final String FIRST_IMAGE = "com.crazy.contentdoesnotmatter.PhotoCrop.FIRST_IMAGE";
	public static final String SECOND_IMAGE = "com.crazy.contentdoesnotmatter.PhotoCrop.SECOND_IMAGE";

	private ImageEditView imgView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_photo_crop,
				container, false);
		imgView = (ImageEditView) rootView.findViewById(R.id.photoEditView);
		SeekBar seekBar = (SeekBar) rootView.findViewById(R.id.alphaSeekBar);
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
		rootView.findViewById(R.id.firstImageButton).setOnClickListener(this);
		rootView.findViewById(R.id.secondImageButton).setOnClickListener(this);
		Bundle arguments = getArguments();
		setFirstImage(arguments.getString(FIRST_IMAGE));
		setSecondImage(arguments.getString(SECOND_IMAGE));
		return rootView;
	}
	
	@Override
	public Bundle returnResult() {
		Bundle result = new Bundle();
		result.putParcelable(SelectorFragment.RESULT, imgView.getCropperBitmap());
		return result;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.firstImageButton:
			imgView.setFirst();
			break;
		case R.id.secondImageButton:
			imgView.setSecond();
			break;
		default:
			break;
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	}

	public String getFirstImage() {
		return firstImage;
	}

	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
		Uri firstImageUri = Uri.parse(firstImage);

		if (firstImageUri.toString().startsWith("http")) {
			new DownloadImageTask(FIRST_IMAGE).execute(firstImage);
		} else {
			try {
				firstImageBitmap = MediaStore.Images.Media.getBitmap(
						PhotoCropFragment.this.getActivity()
								.getContentResolver(), firstImageUri);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		imgView.setBitmaps(firstImageBitmap, secondImageBitmap);
	}

	public String getSecondImage() {
		return secondImage;
	}

	public void setSecondImage(String secondImage) {
		this.secondImage = secondImage;
		Uri secondImageUri = Uri.parse(secondImage);

		if (secondImageUri.toString().startsWith("http")) {
			new DownloadImageTask(SECOND_IMAGE).execute(secondImage);
		} else {
			try {
				secondImageBitmap = MediaStore.Images.Media.getBitmap(
						PhotoCropFragment.this.getActivity()
								.getContentResolver(), secondImageUri);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		imgView.setBitmaps(firstImageBitmap, secondImageBitmap);
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

		private ProgressDialog progressDialog;
		private String imageId;

		public DownloadImageTask(String imageId) {
			this.imageId = imageId;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(
					PhotoCropFragment.this.getActivity(), "Loading",
					"Please wait");
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
			if (imageId == FIRST_IMAGE) {
				imgView.setBitmaps(result, null);
			} else {
				imgView.setBitmaps(null, result);
			}
			progressDialog.dismiss();
		}
	}
}
