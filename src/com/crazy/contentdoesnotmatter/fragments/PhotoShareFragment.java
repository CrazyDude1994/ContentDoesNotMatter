package com.crazy.contentdoesnotmatter.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazy.contentdoesnotmatter.R;

public class PhotoShareFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_photo_share, container, false);
	}
	
	public void saveImage(View view) {
/*		String type = "image/*";
		String caption = "#ContentDoesNotMatter";

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType(type);
		ImageEditView imgView = (ImageEditView) findViewById(R.id.photoEditView);
		intent.putExtra(Intent.EXTRA_STREAM,
				utils.getImageUri(this, imgView.getCropperBitmap()));
		intent.putExtra(Intent.EXTRA_TEXT, caption);
		startActivity(Intent.createChooser(intent, "Share to"));*/
	}

}
