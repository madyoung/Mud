package com.mud.util;

import java.util.Random;

public class StringUtil {
	private static final String stringsBase = "1234567890QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
	private static final int stringsBaseLength = 62;

	public static String randomStrings(int n) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < n; i++) {
			int next = random.nextInt(stringsBaseLength);
			sb.append(stringsBase.charAt(next));
		}
		return sb.toString();
	}

	public static boolean isEmpty(String src) {
		return src == null || src.trim().length() == 0;
	}

	public static boolean isNotEmpty(String src) {
		return src != null && src.trim().length() > 0;
	}

	public static String toHexString(byte data) {
		return Integer.toHexString((data >>> 4) & 0X0F) + Integer.toHexString(data & 0X0F);
	}

	public static String toHexString(byte[] data, String spitchar) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			if (i > 0) {
				buffer.append(spitchar);
			}
			buffer.append(toHexString(data[i]));
		}
		return buffer.toString();
	}

	public static String toHexString(byte[] data) {
		return toHexString(data, "");
	}

}