package com.crazy.contentdoesnotmatter.classes.shaders;

import android.graphics.Color;

import com.crazy.contentdoesnotmatter.classes.PixelShader;

public class SepiaShader extends PixelShader {

	@Override
	public int calculatePixel(int color) {
		int outputRed = (int)((Color.red(color) * 0.393) + (Color.green(color) * 0.769) + (Color.blue(color) * 0.189));
		int outputGreen = (int)((Color.red(color) * 0.349) + (Color.green(color) * 0.686) + (Color.blue(color) * 0.168));
		int outputBlue = (int)((Color.red(color) * 0.272) + (Color.green(color) * 0.534) + (Color.blue(color) * 0.131));
		return Color.rgb(Math.min(outputRed, 255), Math.min(outputGreen, 255), Math.min(outputBlue, 255));
	}

}
