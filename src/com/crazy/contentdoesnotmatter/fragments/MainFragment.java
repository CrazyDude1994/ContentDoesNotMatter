package com.crazy.contentdoesnotmatter.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazy.contentdoesnotmatter.R;
import com.crazy.contentdoesnotmatter.activities.MainActivity;
import com.crazy.contentdoesnotmatter.views.PhotoView;

public class MainFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		((PhotoView)rootView.findViewById(R.id.topImage)).setRotate(-5);
		((PhotoView)rootView.findViewById(R.id.bottomImage)).setRotate(5);
		return rootView;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(MainActivity.TOP_IMAGE, ((PhotoView)getView().findViewById(R.id.topImage)).getImage());
		outState.putParcelable(MainActivity.BOT_IMAGE, ((PhotoView)getView().findViewById(R.id.topImage)).getImage());
		super.onSaveInstanceState(outState);
	}
}
