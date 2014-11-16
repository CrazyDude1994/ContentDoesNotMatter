package com.crazy.contentdoesnotmatter.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;

public class RetainedFragment extends Fragment {

	private LruCache<String, Bitmap> cache;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	public void setData(LruCache<String, Bitmap> data) {
		this.cache = data;
	}

	public LruCache<String, Bitmap> getData() {
		return cache;
	}

}
