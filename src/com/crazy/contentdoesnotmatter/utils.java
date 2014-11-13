package com.crazy.contentdoesnotmatter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
}
