package com.crazy.contentdoesnotmatter.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class PhotoView extends ImageView {
	
	private int rotation = 5;
	
	public int getRotate() {
		return rotation;
	}

	public void setRotate(int rotation) {
		this.rotation = rotation;
		invalidate();
	}

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
		canvas.rotate(this.rotation, getWidth() / 2, getHeight() / 2);
		canvas.scale(0.8f, 0.8f, getWidth() / 2, getHeight() / 2);
		super.draw(canvas);
	}
}
