package com.mojoauth.android.helper;

public class MojoAuthValidator {


	public static boolean isNullOrWhiteSpace(String s) {
		return s == null || isWhitespace(s);

	}

	private static boolean isWhitespace(String s) {
		int length = s.length();
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				if (!Character.isWhitespace(s.charAt(i))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static String getValidationMessage(String s) {
		return "The API Request Paramter" + s + "is not Correct or WellFormated";
	}
}
