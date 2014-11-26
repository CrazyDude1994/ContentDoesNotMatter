package com.crazy.contentdoesnotmatter.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.crazy.contentdoesnotmatter.R;

public class SelectorFragment extends Fragment implements OnClickListener {
	
	public static final String RESULT = "FRAGMENT_RESULT";
	
	public interface OnFragmentChangeListener {
		public void onNextFragment(Fragment receiver, Bundle data);
		public void onPreviousFragment(Fragment receiver, Bundle data);
	}
	
	public interface ResultReturner {
		public Bundle returnResult();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private ArrayList<Fragment> fragmentList;
	private int currentFragmentId = 0;
	private View previousButton;
	private View nextButton;
	
	private OnFragmentChangeListener callback;

	public SelectorFragment() {
		this.fragmentList = new ArrayList<Fragment>();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
            callback = (OnFragmentChangeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentChangeListener");
        }

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
		previousButton = rootView.findViewById(R.id.previousFragmentButton);
		previousButton.setOnClickListener(this);
		nextButton = rootView.findViewById(R.id.nextFragmentButton);
		nextButton.setOnClickListener(this);
		return rootView;
	}

	public void addFragmentToList(Fragment fragment) {
		fragmentList.add(fragment);
	}

	public boolean navigateNextFragment() {
		if (currentFragmentId < fragmentList.size() - 1) {
			Fragment currentFragment = fragmentList.get(currentFragmentId);
			currentFragmentId++;
			Fragment nextFragment = fragmentList.get(currentFragmentId);
			Bundle data = null;
			if (currentFragment instanceof ResultReturner) {
				data = ((ResultReturner) currentFragment).returnResult();
			}
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.photo_placeholder,
							nextFragment).commit();
			if (data != null) {
				callback.onNextFragment(nextFragment, data);
			}
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