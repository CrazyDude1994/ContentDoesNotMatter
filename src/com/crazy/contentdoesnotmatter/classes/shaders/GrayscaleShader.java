package com.crazy.contentdoesnotmatter.classes.shaders;

import android.graphics.Color;

import com.crazy.contentdoesnotmatter.classes.PixelShader;

public class GrayscaleShader extends PixelShader {

	@Override
	public int calculatePixel(int color) {
		int colorComponent = (int)(Color.red(color) * 0.215 + Color.green(color) * 0.7154 + Color.blue(color) * 0.0721);
		return Color.rgb(Math.min(colorComponent, 255), Math.min(colorComponent, 255), Math.min(colorComponent, 255));
	}

}
