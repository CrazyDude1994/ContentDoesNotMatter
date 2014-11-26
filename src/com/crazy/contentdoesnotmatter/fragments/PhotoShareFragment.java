package com.crazy.contentdoesnotmatter.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.crazy.utils.utils;
import com.crazy.contentdoesnotmatter.R;

public class PhotoShareFragment extends Fragment implements OnClickListener {
	
	private Bitmap resultImage;
	
	public static final String EXTRA_BITMAP = "EXTRA_BITMAP";

	public Bitmap getResultImage() {
		return resultImage;
	}

	public void setResultImage(Bitmap resultImage) {
		this.resultImage = resultImage;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_photo_share, container, false);
		rootView.findViewById(R.id.shareButton).setOnClickListener(this);
		resultImage = getArguments().getParcelable(SelectorFragment.RESULT);
		return rootView;
	}
	
	@Override
	public void onClick(View v) {
		saveImage(v);
	}
	
	public void saveImage(View view) {
		String type = "image/*";
		String caption = "#ContentDoesNotMatter";

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType(type);
		intent.putExtra(Intent.EXTRA_STREAM,
				utils.getImageUri(getActivity(), resultImage));
		intent.putExtra(Intent.EXTRA_TEXT, caption);
		startActivity(Intent.createChooser(intent, "Share to"));
	}

}
