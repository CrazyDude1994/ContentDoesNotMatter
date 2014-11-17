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
	public final static int SELECT_GALLERY_PHOTO = 1;
	
	public final static String EXTRA_THUMBNAIL = "com.crazy.contentdoesnotmatter.EXTRA_THUMNAIL";
	public final static String EXTRA_FULL_SIZE = "com.crazy.contentdoesnotmatter.EXTRA_FULL_SIZE";

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
				Bitmap thumbnailBitmap = data.getParcelableExtra(EXTRA_THUMBNAIL);
				String fullSizeURL = data.getStringExtra(EXTRA_FULL_SIZE);
				PhotoView photoView = (PhotoView)findViewById(R.id.firstImage);
				photoView.setImageBitmap(thumbnailBitmap);
			}
			break;
		case SELECT_GALLERY_PHOTO:
			if (resultCode == RESULT_OK) {
				Uri image = data.getData();
	            InputStream imageStream;
				try {
					imageStream = getContentResolver().openInputStream(image);
		            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
		            PhotoView photoView = (PhotoView)findViewById(R.id.secondImage);
		            photoView.setImageBitmap(selectedImage);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
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
		SharedPreferences preferences = getSharedPreferences(getString(R.string.preferense_file_name), Context.MODE_PRIVATE);
		String accessToken = preferences.getString("accessToken", "");
		if (accessToken == "") {
			LoginFragment auth_fragment = new LoginFragment();
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	
			fragmentTransaction.replace(R.id.container, auth_fragment);
			fragmentTransaction.addToBackStack("main");
			fragmentTransaction.commit();
		} else {
			Intent intent = new Intent(this, PhotoViewActivity.class);
			startActivityForResult(intent, SELECT_INSTAGRAM_PHOTO);
		}
	}
	
	public void pressGalleryButton(View view) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, SELECT_GALLERY_PHOTO);
	}
	
}
