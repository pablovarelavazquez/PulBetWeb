package com.pulbet.web.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateUtils {
	
	public static final DateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	public static final DateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat WITH_HOUR_FORMAT =  new SimpleDateFormat("dd/MM/yyyy HH:mm");
	public static final DateFormat WITH_SECOND_FORMAT =  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	
}
