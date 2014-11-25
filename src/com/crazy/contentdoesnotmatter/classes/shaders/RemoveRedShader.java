package com.crazy.contentdoesnotmatter.classes.shaders;

import android.graphics.Color;

import com.crazy.contentdoesnotmatter.classes.PixelShader;

public class RemoveRedShader extends PixelShader {

	@Override
	public int calculatePixel(int color) {
		return Color.rgb(0, Color.green(color), Color.blue(color));
	}

}
