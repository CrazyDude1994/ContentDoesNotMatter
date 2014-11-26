package com.crazy.contentdoesnotmatter.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.crazy.contentdoesnotmatter.R;
import com.crazy.contentdoesnotmatter.fragments.PhotoCropFragment;
import com.crazy.contentdoesnotmatter.fragments.PhotoShareFragment;
import com.crazy.contentdoesnotmatter.fragments.SelectorFragment;

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
			arguments.putString(PhotoCropFragment.FIRST_IMAGE,
					intent.getStringExtra(FIRST_IMAGE));
			arguments.putString(PhotoCropFragment.SECOND_IMAGE,
					intent.getStringExtra(SECOND_IMAGE));
			cropFragment.setArguments(arguments);
			
			SelectorFragment fragmentSwitcher = new SelectorFragment();
			PhotoShareFragment shareFragment = new PhotoShareFragment();
			getFragmentManager().beginTransaction()
					.add(R.id.photo_placeholder, cropFragment)
					.add(R.id.navigation_placeholder, fragmentSwitcher)
					.commit();
			fragmentSwitcher.addFragmentToList(cropFragment);
			fragmentSwitcher.addFragmentToList(shareFragment);
		}
	}
}