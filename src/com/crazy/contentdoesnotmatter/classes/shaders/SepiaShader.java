package com.crazy.contentdoesnotmatter.classes.shaders;

import android.graphics.Color;

import com.crazy.contentdoesnotmatter.classes.PixelShader;

public class SepiaShader extends PixelShader {

	@Override
	public int[] calculatePixels(int[] pixels) {
		int[] result = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			int red = Color.red(pixels[i]);
			int green = Color.green(pixels[i]);
			int blue = Color.blue(pixels[i]);
			int outputRed = (int)((red * 0.393) + (green * 0.769) + (blue * 0.189));
			int outputGreen = (int)((red * 0.349) + (green * 0.686) + (blue * 0.168));
			int outputBlue = (int)((red * 0.272) + (green * 0.534) + (blue * 0.131));
			result[i] = Color.rgb(Math.min(outputRed, 255), Math.min(outputGreen, 255), Math.min(outputBlue, 255));
		}
		return result;
	}

}
