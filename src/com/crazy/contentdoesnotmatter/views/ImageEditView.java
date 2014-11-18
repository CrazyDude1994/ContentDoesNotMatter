package com.crazy.contentdoesnotmatter.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageEditView extends ImageView {
	
	private Bitmap firstBmp;
	private Bitmap secondBmp;
	private Paint paint;
	private int alphaDifference;
	

	public ImageEditView(Context context) {
		super(context);
		paint = new Paint();
	}
	
	public ImageEditView(Context context, Bitmap firstBitmap, Bitmap secondBitmap) {
		super(context);
		this.firstBmp = firstBitmap;
		this.secondBmp = secondBitmap;
		this.paint = new Paint();
		this.alphaDifference = 127;
	}
	
	public ImageEditView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.paint = new Paint();
		this.alphaDifference = 127;
	}

	public ImageEditView(Context context, AttributeSet attrs, int defaultStyle) {
	    super(context, attrs, defaultStyle);
	    this.paint = new Paint();
	    this.alphaDifference = 127;
	}
	
	public void setBitmaps(Bitmap firstBitmap, Bitmap secondBitmap) {
		this.firstBmp = firstBitmap;
		this.secondBmp = secondBitmap;
	}
	
	public void setAlpaDifference(int alpha) {
		this.alphaDifference = alpha;
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int difference = 255 - alphaDifference;
		paint.setAlpha(this.alphaDifference);
		canvas.drawBitmap(firstBmp, getImageMatrix(), paint);
		paint.setAlpha(difference);
		canvas.drawBitmap(secondBmp, getImageMatrix(), paint);
		super.onDraw(canvas);
	}
	
}