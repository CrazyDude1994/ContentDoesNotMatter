package com.crazy.contentdoesnotmatter.classes.shaders;

import android.graphics.Color;

import com.crazy.contentdoesnotmatter.classes.PixelShader;

public class RemoveBlueShader extends PixelShader {

	@Override
	public int calculatePixel(int color) {
		return Color.rgb(Color.red(color), Color.green(color), 0);
	}

}
