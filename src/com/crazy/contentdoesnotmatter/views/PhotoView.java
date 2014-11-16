package com.crazy.contentdoesnotmatter.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class PhotoView extends ImageView {
	
	public PhotoView(Context context) {
		super(context);
	}
	
	public PhotoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PhotoView(Context context, AttributeSet attrs, int defaultStyle) {
	    super(context, attrs, defaultStyle);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.save();
		canvas.rotate(45);
		super.draw(canvas);
		canvas.restore();
	}
}
