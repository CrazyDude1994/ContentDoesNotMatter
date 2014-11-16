package com.crazy.contentdoesnotmatter.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.widget.GridView;

import com.crazy.contentdoesnotmatter.R;
import com.crazy.contentdoesnotmatter.adapters.InstagramAdapter;
import com.crazy.contentdoesnotmatter.fragments.RetainedFragment;

public class PhotoViewActivity extends Activity {

	private InstagramAdapter adapter;
	private RetainedFragment retainedFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_view);
		SharedPreferences preferences = getSharedPreferences(
				getString(R.string.preferense_file_name), Context.MODE_PRIVATE);
		String accessToken = preferences.getString("accessToken", "");
		
		FragmentManager fm = getFragmentManager();
		retainedFragment = (RetainedFragment)fm.findFragmentByTag("data");
		
		if (retainedFragment == null) {
			retainedFragment = new RetainedFragment();
			fm.beginTransaction().add(retainedFragment, "data").commit();
		    int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		    int cacheSize = maxMemory / 8;
		    retainedFragment.setData(new LruCache<String, Bitmap>(cacheSize));
		}
		GridView gridView = (GridView) findViewById(R.id.instagramView);
		adapter = new InstagramAdapter(gridView, accessToken, retainedFragment.getData());
		gridView.setAdapter(adapter);
	}
}
