package com.crazy.contentdoesnotmatter.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.crazy.contentdoesnotmatter.R;
import com.crazy.contentdoesnotmatter.fragments.PhotoCropFragment;

public class PhotoEditActivity extends Activity {

	public static final String FIRST_IMAGE = "com.crazy.contentdoesnotmatter.EditActivity.FIRST_IMAGE";
	public static final String SECOND_IMAGE = "com.crazy.contentdoesnotmatter.EditActivity.SECOND_IMAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_edit);

		if (savedInstanceState == null) {
			PhotoCropFragment cropFragment = new PhotoCropFragment();
			Intent intent = getIntent();
			Bundle arguments = new Bundle();
			arguments.putString(PhotoCropFragment.FIRST_IMAGE, intent.getStringExtra(FIRST_IMAGE));
			arguments.putString(PhotoCropFragment.SECOND_IMAGE, intent.getStringExtra(SECOND_IMAGE));
			cropFragment.setArguments(arguments);
			getFragmentManager().beginTransaction()
					.add(R.id.photo_placeholder, cropFragment).commit();
		}
	}
}
