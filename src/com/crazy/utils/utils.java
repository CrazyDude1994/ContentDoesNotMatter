package com.crazy.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore.Images;

public class utils {
	public static final String StreamToString(InputStream stream) {
		BufferedReader bReader = new BufferedReader(new InputStreamReader(
				stream));
		String temp, response = "";
		try {
			while ((temp = bReader.readLine()) != null)
				response += temp;
		} catch (Exception e) {
			return "";
		}
		
		return response;
	}
	
	public static final Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(inContext.getContentResolver(),
				inImage, "Title", null);
		return Uri.parse(path);
	}
}
