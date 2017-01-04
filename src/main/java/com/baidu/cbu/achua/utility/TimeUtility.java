package com.baidu.cbu.achua.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtility {

	public static String getUTCTime(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		TimeZone tz = TimeZone.getTimeZone("UTC");
		df.setTimeZone(tz);
		return df.format(date);
	}
}
