package com.crazy.contentdoesnotmatter.activities;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
		
		try {
			firstImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), firstImageUri);
			secondImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), secondImageUri);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final ImageEditView imgView = (ImageEditView)findViewById(R.id.imageView1);
		imgView.setBitmaps(firstImageBitmap, secondImageBitmap);
		
		SeekBar seekBar = (SeekBar)findViewById(R.id.alphaSeekBar);
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

}
