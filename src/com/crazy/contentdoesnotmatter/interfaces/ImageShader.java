package com.crazy.contentdoesnotmatter.interfaces;

import android.graphics.Bitmap;

public interface ImageShader {
	public int[] calculatePixels(int[] pixels);
	public void applyShader(Bitmap bitmap);
}
