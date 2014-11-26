package com.crazy.contentdoesnotmatter.classes.shaders;

import android.graphics.Color;

import com.crazy.contentdoesnotmatter.classes.PixelShader;

public class GrayscaleShader extends PixelShader {

	@Override
	public int[] calculatePixels(int[] pixels) {
		int[] result = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			int color = pixels[i];
			int colorComponent = (int)(Color.red(color) * 0.215 + Color.green(color) * 0.7154 + Color.blue(color) * 0.0721);
			result[i] = Color.rgb(Math.min(colorComponent, 255), Math.min(colorComponent, 255), Math.min(colorComponent, 255));
		}
		return result;
	}

}
