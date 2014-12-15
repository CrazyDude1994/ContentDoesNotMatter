package com.crazy.contentdoesnotmatter.classes.shaders;

import android.graphics.Color;

import com.crazy.contentdoesnotmatter.classes.PixelShader;

public class RemoveRedShader extends PixelShader {

	@Override
	public int[] calculatePixels(int[] pixels) {
		int[] result = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			int color = pixels[i];
			result[i] = Color.rgb(0, Color.green(color), Color.blue(color));
		}
		return result;
	}

}
