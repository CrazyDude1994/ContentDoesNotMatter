package com.crazy.contentdoesnotmatter.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ShaderAdapter extends BaseAdapter {
	
	private ArrayList<Bitmap> mObjects;
	private Context mContext;

	public ShaderAdapter(Context context) {
		mObjects = new ArrayList<Bitmap>();
		mContext = context;
	}

	@Override
	public int getCount() {
		return mObjects.size();
	}

	@Override
	public Bitmap getItem(int index) {
		return mObjects.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView view = (ImageView) convertView;
		if (view == null) {
			view = new ImageView(mContext);
		}
		return view;
	}

}
