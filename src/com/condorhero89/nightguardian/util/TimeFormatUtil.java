package com.condorhero89.nightguardian.util;

public class TimeFormatUtil {

	public static String getTimeFormat(int hourOrMinute) {
		if (hourOrMinute < 10) {
			return "0" + hourOrMinute;
		}
		
		return String.valueOf(hourOrMinute);
	}
}
