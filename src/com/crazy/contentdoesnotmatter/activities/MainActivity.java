package com.crazy.contentdoesnotmatter.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import com.crazy.contentdoesnotmatter.R;
import com.crazy.contentdoesnotmatter.fragments.LoginFragment;
import com.crazy.contentdoesnotmatter.views.PhotoView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends Activity {

	public final static int SELECT_INSTAGRAM_PHOTO = 0;
	public final static int SELECT_TOP_GALLERY_PHOTO = 1;
	public final static int SELECT_BOT_GALLERY_PHOTO = 2;

	public final static String EXTRA_THUMBNAIL = "com.crazy.contentdoesnotmatter.EXTRA_THUMBNAIL";
	public final static String EXTRA_FULL_SIZE = "com.crazy.contentdoesnotmatter.EXTRA_FULL_SIZE";
	public final static String EXTRA_VIEW_ID = "com.crazy.contentdoesnotmatter.EXTRA_VIEW_ID";
	
	public final static String TOP_IMAGE = "com.crazy.contentdoesnotmatter.EXTRA_TOP_IMAGE";
	public final static String BOT_IMAGE = "com.crazy.contentdoesnotmatter.EXTRA_BOT_IMAGE";

	private String topImageURI;
	private String botImageURI;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState != null) {
			String topImage = savedInstanceState.getString(TOP_IMAGE);
			String botImage = savedInstanceState.getString(BOT_IMAGE);
			if (topImage != "")
				topImageURI = topImage;
			if (botImage != "")
				botImageURI = botImage;


		}
		if (topImageURI != null && botImageURI != null) {
			findViewById(R.id.startEditButton).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.id.startEditButton).setVisibility(View.INVISIBLE);
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString(TOP_IMAGE, topImageURI);
		outState.putString(BOT_IMAGE, botImageURI);
		super.onSaveInstanceState(outState);
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
				photoView.setImage(thumbnailBitmap);
				if (callerId == R.id.topImage) {
					topImageURI = fullSizeURL;
				} else {
					botImageURI = fullSizeURL;
				}
			}
			break;
		case SELECT_TOP_GALLERY_PHOTO:
		case SELECT_BOT_GALLERY_PHOTO:
			if (resultCode == RESULT_OK) {	
				Uri image = data.getData();
				InputStream imageStream;
				try {
					imageStream = getContentResolver().openInputStream(image);
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.outHeight = 150;
					options.outWidth = 150;
					Bitmap selectedImage = BitmapFactory
							.decodeStream(imageStream, null, options);
					PhotoView photoView;
					if (requestCode == SELECT_BOT_GALLERY_PHOTO) {
						photoView = (PhotoView)findViewById(R.id.bottomImage);
						botImageURI = image.toString();
					} else {
						photoView = (PhotoView)findViewById(R.id.topImage);
						topImageURI = image.toString();
					}
					photoView.setImage(selectedImage);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}

		if (topImageURI != null && botImageURI != null) {
			findViewById(R.id.startEditButton).setVisibility(View.VISIBLE);
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

			fragmentTransaction.add(R.id.container, auth_fragment);
			fragmentTransaction.addToBackStack(null);
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

	public void startEdit(View view) {
		Intent intent = new Intent(this, PhotoEditActivity.class);
		intent.putExtra(PhotoEditActivity.FIRST_IMAGE, topImageURI);
		intent.putExtra(PhotoEditActivity.SECOND_IMAGE, botImageURI);

		startActivity(intent);
	}

	public String getTopImageURI() {
		return topImageURI;
	}

	public String getBotImageURI() {
		return botImageURI;
	}
}
