package com.crazy.contentdoesnotmatter.classes;

import android.graphics.Bitmap;

import com.crazy.contentdoesnotmatter.interfaces.ImageShader;

public abstract class PixelShader implements ImageShader {
	
	public abstract int[] calculatePixels(int[] pixels);
	
	public void applyShader(Bitmap bitmap) {
		int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
		bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
		int[] result = calculatePixels(pixels);
		bitmap.setPixels(result, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
	}

}
