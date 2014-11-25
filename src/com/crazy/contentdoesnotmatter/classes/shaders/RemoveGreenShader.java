package com.crazy.contentdoesnotmatter.classes.shaders;

import android.graphics.Color;

import com.crazy.contentdoesnotmatter.classes.PixelShader;

public class RemoveGreenShader extends PixelShader {

	@Override
	public int calculatePixel(int color) {
		return Color.rgb(Color.red(color), 0, Color.blue(color));
	}

}
