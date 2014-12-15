package com.crazy.contentdoesnotmatter.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.crazy.contentdoesnotmatter.R;
import com.crazy.contentdoesnotmatter.classes.PixelShader;
import com.crazy.contentdoesnotmatter.classes.shaders.BlueberryShader;
import com.crazy.contentdoesnotmatter.classes.shaders.GrayscaleShader;
import com.crazy.contentdoesnotmatter.classes.shaders.RemoveBlueShader;
import com.crazy.contentdoesnotmatter.classes.shaders.RemoveGreenShader;
import com.crazy.contentdoesnotmatter.classes.shaders.RemoveRedShader;
import com.crazy.contentdoesnotmatter.classes.shaders.SepiaShader;
import com.crazy.contentdoesnotmatter.fragments.SelectorFragment.ResultReturner;

public class PhotoEffectsFragment extends Fragment implements ResultReturner {
	
	private static final ArrayList<PixelShader> shaderList = new ArrayList<PixelShader>();
	
	private Bitmap resultImage;
	private Bitmap originalImage;
	private ImageView imgView;
	
	private void initShaderList() {
		shaderList.add(new BlueberryShader());
		shaderList.add(new GrayscaleShader());
		shaderList.add(new RemoveBlueShader());
		shaderList.add(new RemoveGreenShader());
		shaderList.add(new RemoveRedShader());
		shaderList.add(new SepiaShader());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_photo_effects,
				container, false);
		originalImage = getArguments().getParcelable(SelectorFragment.RESULT);
		imgView = (ImageView)rootView.findViewById(R.id.imageEditEffect);
		resultImage = Bitmap.createBitmap(originalImage);
		imgView.setImageBitmap(resultImage);
		
		initShaderList();
		
		LinearLayout layout = (LinearLayout)rootView.findViewById(R.id.shaderList);
		
		for (final PixelShader shader : shaderList) {
			ImageView view = new ImageView(rootView.getContext());
			Bitmap bitmap = Bitmap.createScaledBitmap(originalImage, 100, 100, false);
			shader.applyShader(bitmap);
			view.setImageBitmap(bitmap);
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					resultImage = Bitmap.createBitmap(originalImage);
					shader.applyShader(resultImage);
					imgView.setImageBitmap(resultImage);
				}
			});
			layout.addView(view);
		}
		
		return rootView;
	}
	

	@Override
	public Bundle returnResult() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(SelectorFragment.RESULT, resultImage);
		return bundle;
	}
}
