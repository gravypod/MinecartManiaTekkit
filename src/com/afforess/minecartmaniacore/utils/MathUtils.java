package com.afforess.minecartmaniacore.utils;

public class MathUtils {

	public static double range(double value, double max, double min){
		if (value > max) value = max;
		else if (value < min) value = min;
		return value;
	}
	
	public static int range(int value, int max, int min){
		if (value > max) value = max;
		else if (value < min) value = min;
		return value;
	}
}
