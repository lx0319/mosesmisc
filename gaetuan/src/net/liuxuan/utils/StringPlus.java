package net.liuxuan.utils;


import java.io.BufferedReader;
import java.io.IOException;

public class StringPlus {
	public static String BufferedReaderToString(BufferedReader bufferedReader) {
		if (bufferedReader == null)
			return null;
		StringBuffer result = new StringBuffer();
		String line = null;
		try {			
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e) {
			return null;
		}
		return result.toString();
	}
}
