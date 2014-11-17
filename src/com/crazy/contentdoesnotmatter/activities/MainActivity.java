package com.crazy.contentdoesnotmatter.activities;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazy.contentdoesnotmatter.R;
import com.crazy.contentdoesnotmatter.fragments.LoginFragment;
import com.crazy.contentdoesnotmatter.views.PhotoView;

public class MainActivity extends Activity {

	public final static int SELECT_INSTAGRAM_PHOTO = 0;
	public final static int SELECT_TOP_GALLERY_PHOTO = 1;
	public final static int SELECT_BOT_GALLERY_PHOTO = 2;

	public final static String EXTRA_THUMBNAIL = "com.crazy.contentdoesnotmatter.EXTRA_THUMNAIL";
	public final static String EXTRA_FULL_SIZE = "com.crazy.contentdoesnotmatter.EXTRA_FULL_SIZE";
	public final static String EXTRA_VIEW_ID = "com.crazy.contentdoesnotmatter.EXTRA_VIEW_ID";

	private String firstImageURL = "";
	private String secondImageURL = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case SELECT_INSTAGRAM_PHOTO:
			if (resultCode == RESULT_OK) {
				Bitmap thumbnailBitmap = data
						.getParcelableExtra(EXTRA_THUMBNAIL);
				String fullSizeURL = data.getStringExtra(EXTRA_FULL_SIZE);
				int callerId = data.getIntExtra(EXTRA_VIEW_ID, 0);
				PhotoView photoView = (PhotoView) findViewById(callerId);
				photoView.setImageBitmap(thumbnailBitmap);
				firstImageURL = "lol";
			}
			break;
		case SELECT_TOP_GALLERY_PHOTO:
		case SELECT_BOT_GALLERY_PHOTO:
			if (resultCode == RESULT_OK) {	
				Uri image = data.getData();
				InputStream imageStream;
				try {
					imageStream = getContentResolver().openInputStream(image);
					Bitmap selectedImage = BitmapFactory
							.decodeStream(imageStream);
					PhotoView photoView;
					if (requestCode == SELECT_BOT_GALLERY_PHOTO) {
						photoView = (PhotoView)findViewById(R.id.bottomImage);
					} else {
						photoView = (PhotoView)findViewById(R.id.topImage);
					}
					photoView.setImageBitmap(Bitmap.createScaledBitmap(selectedImage, 150, 150, false));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				secondImageURL = "lol";
			}
			break;
		default:
			break;
		}

		if (firstImageURL != "" && secondImageURL != "") {
			findViewById(R.id.startEditButton).setVisibility(View.VISIBLE);
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	public void pressInstagramButton(View view) {
		SharedPreferences preferences = getSharedPreferences(
				getString(R.string.preferense_file_name), Context.MODE_PRIVATE);
		String accessToken = preferences.getString("accessToken", "");
		if (accessToken == "") {
			LoginFragment auth_fragment = new LoginFragment();
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();

			fragmentTransaction.replace(R.id.container, auth_fragment);
			fragmentTransaction.addToBackStack("main");
			fragmentTransaction.commit();
		} else {
			Intent intent = new Intent(this, PhotoViewActivity.class);
			switch (view.getId()) {
			case R.id.topInstagramButton:
				intent.putExtra(EXTRA_VIEW_ID, R.id.topImage);
				break;
				
			case R.id.bottomInstagramButton:
				intent.putExtra(EXTRA_VIEW_ID, R.id.bottomImage);
				break;

			default:
				break;
			}
			startActivityForResult(intent, SELECT_INSTAGRAM_PHOTO);
		}
	}

	public void pressGalleryButton(View view) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		switch (view.getId()) {
		case R.id.topGalleryButton:
			startActivityForResult(intent, SELECT_TOP_GALLERY_PHOTO);
			break;

		case R.id.bottomGalleryButton:
			startActivityForResult(intent, SELECT_BOT_GALLERY_PHOTO);
			break;

		default:
			break;
		}
	}

}
