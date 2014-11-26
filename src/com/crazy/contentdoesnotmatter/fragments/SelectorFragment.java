package com.crazy.contentdoesnotmatter.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.crazy.contentdoesnotmatter.R;

public class SelectorFragment extends Fragment implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private ArrayList<Fragment> fragmentList;
	private int currentFragmentId = 0;

	public SelectorFragment() {
		this.fragmentList = new ArrayList<Fragment>();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nextFragmentButton:
			navigateNextFragment();
			break;

		case R.id.previousFragmentButton:
			navigatePreviousFragment();
			break;

		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_switcher, container,
				false);
		rootView.findViewById(R.id.previousFragmentButton).setOnClickListener(
				this);
		rootView.findViewById(R.id.nextFragmentButton).setOnClickListener(this);
		return rootView;
	}

	public void addFragmentToList(Fragment fragment) {
		fragmentList.add(fragment);
	}

	public boolean navigateNextFragment() {
		if (currentFragmentId < fragmentList.size() - 1) {
			currentFragmentId++;
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.photo_placeholder,
							fragmentList.get(currentFragmentId)).commit();
			return true;
		} else {
			return false;
		}
	}

	public boolean navigatePreviousFragment() {
		if (0 < currentFragmentId) {
			currentFragmentId--;
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.photo_placeholder,
							fragmentList.get(currentFragmentId)).commit();
			return true;
		} else {
			return false;
		}
	}
}