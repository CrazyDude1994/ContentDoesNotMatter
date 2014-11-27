package com.crazy.contentdoesnotmatter.classes.shaders;

import android.graphics.Color;

import com.crazy.contentdoesnotmatter.classes.PixelShader;

public class BlueberryShader extends PixelShader {

	@Override
	public int[] calculatePixels(int[] pixels) {
		int[] result = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			int red = Math.max(Color.red(pixels[i]) - 40, 0);
			int green = Math.max(Color.green(pixels[i]) - 48, 0);
			int blue = Math.max(Color.blue(pixels[i]) - 10, 0);
			result[i] = Color.rgb(red, green, blue);
		}
		return result;
	}

}
