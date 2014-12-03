package com.crazy.contentdoesnotmatter.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.crazy.contentdoesnotmatter.R;
import com.crazy.contentdoesnotmatter.classes.shaders.BlueberryShader;
import com.crazy.contentdoesnotmatter.classes.shaders.GrayscaleShader;
import com.crazy.contentdoesnotmatter.classes.shaders.SepiaShader;
import com.crazy.contentdoesnotmatter.fragments.SelectorFragment.ResultReturner;

public class PhotoEffectsFragment extends Fragment implements OnClickListener, ResultReturner {
	
	private Bitmap resultImage;
	private Bitmap originalImage;
	private ImageView imgView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_photo_effects,
				container, false);
		rootView.findViewById(R.id.sepiaButton).setOnClickListener(this);
		rootView.findViewById(R.id.grayScaleButton).setOnClickListener(this);
		rootView.findViewById(R.id.blueBerryButton).setOnClickListener(this);
		originalImage = getArguments().getParcelable(SelectorFragment.RESULT);
		imgView = (ImageView)rootView.findViewById(R.id.imageEditEffect);
		resultImage = Bitmap.createBitmap(originalImage);
		imgView.setImageBitmap(resultImage);
		return rootView;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sepiaButton:	
			resultImage = Bitmap.createBitmap(originalImage);
			new SepiaShader().applyShader(resultImage);
			imgView.setImageBitmap(resultImage);
			break;
			
		case R.id.grayScaleButton:
			resultImage = Bitmap.createBitmap(originalImage);
			new GrayscaleShader().applyShader(resultImage);
			imgView.setImageBitmap(resultImage);
			break;
			
		case R.id.blueBerryButton:
			resultImage = Bitmap.createBitmap(originalImage);
			new BlueberryShader().applyShader(resultImage);
			imgView.setImageBitmap(resultImage);
			break;

		default:
			break;
		}
		
	}

	@Override
	public Bundle returnResult() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(SelectorFragment.RESULT, resultImage);
		return bundle;
	}
}
