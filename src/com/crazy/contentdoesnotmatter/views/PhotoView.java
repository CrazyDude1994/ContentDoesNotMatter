package com.crazy.contentdoesnotmatter.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class PhotoView extends ImageView {
	
	private int rotation = 0;
	
	private Bitmap image;
	
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
		if (getImage() != null) {
			int height = canvas.getHeight();
			int width = canvas.getWidth();
			canvas.save();
			canvas.scale(0.9f, 0.9f, getWidth() / 2, getHeight() / 2);
			canvas.drawBitmap(getImage(), null, new Rect(0, 0, width, height), null);
			canvas.restore();
		}
		super.draw(canvas);
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
		invalidate();
	}
}
