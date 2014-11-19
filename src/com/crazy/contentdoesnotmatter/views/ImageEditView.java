package com.crazy.contentdoesnotmatter.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class ImageEditView extends View implements
		ScaleGestureDetector.OnScaleGestureListener {

	private class DrawableInfo {
		private Bitmap bitMap;
		private Matrix matrix;

		public DrawableInfo(Bitmap bitmap, Matrix matrix) {
			this.setBitMap(bitmap);
			if (matrix == null)
				this.setMatrix(new Matrix());
			else
				this.setMatrix(matrix);
		}

		public void setBitMap(Bitmap bitMap) {
			this.bitMap = bitMap;
		}

		public void setMatrix(Matrix matrix) {
			this.matrix = matrix;
		}

		public Bitmap getBitMap() {
			return bitMap;
		}

		public Matrix getMatrix() {
			return matrix;
		}
	}

	private DrawableInfo firstImage, secondImage;
	private DrawableInfo currentImage;
	private Paint paint;
	private int alphaDifference;
	private ScaleGestureDetector scaleDetector;
	private float oldX, oldY;

	public ImageEditView(Context context) {
		super(context);
		this.paint = new Paint();
		this.alphaDifference = 127;
		this.firstImage = new DrawableInfo(null, null);
		this.secondImage = new DrawableInfo(null, null);
		this.scaleDetector = new ScaleGestureDetector(context, this);
		this.setDrawingCacheEnabled(true);
		setFirst();
	}

	public ImageEditView(Context context, Bitmap firstBitmap,
			Bitmap secondBitmap) {
		super(context);
		this.paint = new Paint();
		this.alphaDifference = 127;
		this.scaleDetector = new ScaleGestureDetector(context, this);
		this.firstImage = new DrawableInfo(firstBitmap, null);
		this.secondImage = new DrawableInfo(secondBitmap, null);
		this.setDrawingCacheEnabled(true);
		setFirst();
	}

	public ImageEditView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.paint = new Paint();
		this.alphaDifference = 127;
		this.firstImage = new DrawableInfo(null, null);
		this.secondImage = new DrawableInfo(null, null);
		this.scaleDetector = new ScaleGestureDetector(context, this);
		this.setDrawingCacheEnabled(true);
		setFirst();
	}

	public ImageEditView(Context context, AttributeSet attrs, int defaultStyle) {
		super(context, attrs, defaultStyle);
		this.paint = new Paint();
		this.alphaDifference = 127;
		this.firstImage = new DrawableInfo(null, null);
		this.secondImage = new DrawableInfo(null, null);
		this.scaleDetector = new ScaleGestureDetector(context, this);
		this.setDrawingCacheEnabled(true);
		setFirst();
	}

	public Bitmap getCropperBitmap() {
		return getDrawingCache();
	}

	public void setBitmaps(Bitmap firstBitmap, Bitmap secondBitmap) {
		if (firstBitmap != null)
			firstImage.setBitMap(firstBitmap);
		if (secondBitmap != null)
			secondImage.setBitMap(secondBitmap);
		invalidate();
	}

	public void setFirst() {
		this.currentImage = firstImage;
	}

	public void setSecond() {
		this.currentImage = secondImage;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.scaleDetector.onTouchEvent(event);
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_MOVE:
			moveImage((int) (oldX - event.getRawX()),
					(int) (oldY - event.getRawY()));
			oldX = event.getRawX();
			oldY = event.getRawY();
			break;
		case MotionEvent.ACTION_DOWN:
			oldX = event.getRawX();
			oldY = event.getRawY();
			break;
		default:
			break;
		}
		return true;
	}

	public void moveImage(int x, int y) {
		currentImage.matrix.postTranslate(x, y);
		invalidate();
	}

	public void scaleImage(float scaleFactor, float pivotX, float pivotY) {
		currentImage.matrix.postScale(scaleFactor, scaleFactor, pivotX, pivotY);
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

			if (firstImage.getBitMap() != null) {
				canvas.drawBitmap(firstImage.getBitMap(),
						this.firstImage.getMatrix(), paint);
			}
			paint.setAlpha(difference);
			if (secondImage.getBitMap() != null) {
				canvas.drawBitmap(secondImage.getBitMap(),
						this.secondImage.getMatrix(), paint);
			}
		}
		super.onDraw(canvas);
	}

	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		float scaleFactor = Math.max(0.1f,
				Math.min(detector.getScaleFactor(), 5.0f));
		scaleImage(scaleFactor, detector.getFocusX(), detector.getFocusY());
		return true;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
	}
}