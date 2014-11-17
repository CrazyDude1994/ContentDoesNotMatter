package com.crazy.contentdoesnotmatter.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.crazy.contentdoesnotmatter.R;

public class PhotoEditActivity extends Activity {
	
	private String firstImage;
	private String secondImage;
	
	private static final String FIRST_IMAGE = "com.crazy.contentdoesnotmatter.FIRST_IMAGE";
	private static final String SECOND_IMAGE = "com.crazy.contentdoesnotmatter.SECOND_IMAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_edit);
		
		Intent intent = getIntent();
		firstImage = intent.getStringExtra(FIRST_IMAGE);
		secondImage = intent.getStringExtra(SECOND_IMAGE);
	}

}
