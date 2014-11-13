package com.crazy.contentdoesnotmatter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;

public class PhotoViewActivity extends Activity {

	InstagramAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_view);
		SharedPreferences preferences = getSharedPreferences(getString(R.string.preferense_file_name), Context.MODE_PRIVATE);
		String accessToken = preferences.getString("accessToken", "");

		adapter = new InstagramAdapter(accessToken);
		GridView gridView = (GridView) findViewById(R.id.instagramView);
		gridView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo_view, menu);
		return true;
	}
}
