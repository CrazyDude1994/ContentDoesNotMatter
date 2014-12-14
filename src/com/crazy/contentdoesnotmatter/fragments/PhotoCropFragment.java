package com.crazy.contentdoesnotmatter.fragments;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.crazy.contentdoesnotmatter.views.PhotoView;
import com.crazy.utils.utils;

public class PhotoCropFragment extends Fragment implements OnClickListener, ResultReturner {

	private String firstImage;
	private String secondImage;

	private Bitmap firstImageBitmap;
	private Bitmap secondImageBitmap;
	
	private PhotoView firstButton;
	private PhotoView secondButton;

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
		firstButton = (PhotoView)rootView.findViewById(R.id.firstImageButton);
		secondButton = (PhotoView)rootView.findViewById(R.id.secondImageButton);
		firstButton.setOnClickListener(this);
		secondButton.setOnClickListener(this);
		firstButton.setRotate(5);
		secondButton.setRotate(-5);
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
				AssetFileDescriptor fileDescriptor = getActivity().getContentResolver().openAssetFileDescriptor(firstImageUri, "r");
				firstImageBitmap = utils.decodeSampledBitmapFromFile(fileDescriptor, 600, 600);
				firstButton.setImage(Bitmap.createScaledBitmap(firstImageBitmap, 100, 100, false));
			} catch (FileNotFoundException e) {
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
				AssetFileDescriptor fileDescriptor = getActivity().getContentResolver().openAssetFileDescriptor(secondImageUri, "r");
				secondImageBitmap = utils.decodeSampledBitmapFromFile(fileDescriptor, 600, 600);
				secondButton.setImage(Bitmap.createScaledBitmap(secondImageBitmap, 100, 100, false));
			} catch (FileNotFoundException e) {
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
				firstButton.setImage(Bitmap.createScaledBitmap(result, 100, 100, false));
			} else {
				imgView.setBitmaps(null, result);
				secondButton.setImage(Bitmap.createScaledBitmap(result, 100, 100, false));
			}
			progressDialog.dismiss();
		}
	}
}
