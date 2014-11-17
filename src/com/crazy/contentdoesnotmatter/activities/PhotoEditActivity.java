package com.crazy.contentdoesnotmatter.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.crazy.contentdoesnotmatter.R;

public class PhotoEditActivity extends Activity {
	
	private Bitmap firstImage;
	private Bitmap secondImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_edit);
		
		Intent intent = getIntent();
	}

}
