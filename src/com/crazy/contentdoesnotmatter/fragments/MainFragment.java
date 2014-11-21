package com.crazy.contentdoesnotmatter.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazy.contentdoesnotmatter.R;
import com.crazy.contentdoesnotmatter.views.PhotoView;

public class MainFragment extends Fragment {
	
	private static final String TOP_IMAGE = "EXTRA_TOP_IMAGE";
	private static final String BOT_IMAGE = "EXTRA_BOT_IMAGE";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		((PhotoView)rootView.findViewById(R.id.topImage)).setRotate(-5);
		((PhotoView)rootView.findViewById(R.id.bottomImage)).setRotate(5);
		
		if (savedInstanceState != null) {
			Bitmap topImage = savedInstanceState.getParcelable(TOP_IMAGE);
			Bitmap botImage = savedInstanceState.getParcelable(BOT_IMAGE);
			
			if (topImage != null)
				((PhotoView)rootView.findViewById(R.id.topImage)).setImage(topImage);
			if (botImage != null)
				((PhotoView)rootView.findViewById(R.id.bottomImage)).setImage(botImage);
		}
		return rootView;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(TOP_IMAGE, ((PhotoView)getView().findViewById(R.id.topImage)).getImage());
		outState.putParcelable(BOT_IMAGE, ((PhotoView)getView().findViewById(R.id.bottomImage)).getImage());
		super.onSaveInstanceState(outState);
	}
}
