package com.afforess.minecartmaniacore.utils;

public class StringUtils {
	
	
	public static String getNumber(String s)
	{
		String n = "";
		for (int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			if (Character.isDigit(c) || c == '.' || c == '-')
				n += c;
		}
		return n;
	}
	
	public static boolean containsLetters(String s) {
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.isLetter(c)) {
				return true;
			}
		}
		return false;
	}
	
	
	public static String removeWhitespace(String s) {
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != ' ') 
				s1 += s.charAt(i);
		}
		return s1;
	}
	
	public static String removeBrackets(String s) {
		String str = "";
		boolean isStation = false;
		if (s.toLowerCase().contains("st-")) { //see if we need to make sure [ ] in the middle do not get removed. 
			                                   //Also lower case because the same sign and line will come in as "st-" AND "St-"
			isStation = true;
		} 
		for (int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			if (c == ']' || c == '[') {
				if (!isStation) continue;    // we have a non-station string so remove all brackets 
				// we have a station string if we got this far
				if (i == 0 && c == '[') continue; //only strip beginning [ bracket.
				if (i == s.length()-1 && c == ']' && s.charAt(0) == '[') continue; //only strip ending bracket if a beginning bracket exists
			}
			str += c;
		}

		return str;
	}
	
	public static String addBrackets(String s) {
		return "[" + removeBrackets(s) + "]";
	}
	
	/**
	 * Join an array command into a String
	 * @author Hidendra
	 * @param arr
	 * @param offset
	 * @return
	 */
	public static String join(String[] arr, int offset) {
		return join(arr, offset, " ");
	}

	/**
	 * Join an array command into a String
	 * @author Hidendra
	 * @param arr
	 * @param offset
	 * @param delim
	 * @return
	 */
	public static String join(String[] arr, int offset, String delim) {
		String str = "";

		if (arr == null || arr.length == 0) {
			return str;
		}

		for (int i = offset; i < arr.length; i++) {
			str += arr[i] + delim;
		}

		return str.trim();
	}
}
