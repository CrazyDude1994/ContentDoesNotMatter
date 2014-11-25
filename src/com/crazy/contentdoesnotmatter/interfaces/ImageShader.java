package com.crazy.contentdoesnotmatter.interfaces;

import android.graphics.Bitmap;

public interface ImageShader {
	public int calculatePixel(int color);
	public void applyShader(Bitmap bitmap);
}
