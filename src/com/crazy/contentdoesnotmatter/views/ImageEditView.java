package com.crazy.contentdoesnotmatter.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
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
		this.alphaDifference = 127;
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
		if (firstBitmap != null)
			this.firstBmp = firstBitmap;
		if (secondBitmap != null)
			this.secondBmp = secondBitmap;
		invalidate();
	}
	
	public void setAlpaDifference(int alpha) {
		this.alphaDifference = alpha;
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (!isInEditMode()) {
			int difference = 255 - alphaDifference;
			paint.setAlpha(this.alphaDifference);
			if (firstBmp != null) {
				canvas.drawBitmap(firstBmp, 0, 0, paint);
			}
			paint.setAlpha(difference);
			if (secondBmp != null) {
				canvas.drawBitmap(secondBmp, 0, 0, paint);
			}
		}
		super.onDraw(canvas);
	}
	
}