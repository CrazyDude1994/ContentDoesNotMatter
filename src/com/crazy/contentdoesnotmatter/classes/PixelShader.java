package com.crazy.contentdoesnotmatter.classes;

import android.graphics.Bitmap;

import com.crazy.contentdoesnotmatter.interfaces.ImageShader;

public abstract class PixelShader implements ImageShader {
	
	public abstract int calculatePixel(int color);
	
	public void applyShader(Bitmap bitmap) {
		for (int x = 0; x < bitmap.getWidth(); x++) {
			for (int y = 0; y < bitmap.getHeight(); y++) {
				bitmap.setPixel(x, y, calculatePixel(bitmap.getPixel(x, y)));
			}
		}
	}

}
