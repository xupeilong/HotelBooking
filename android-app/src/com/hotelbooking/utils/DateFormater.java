package com.hotelbooking.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormater {
	public static String format1(Date timestamp)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(timestamp);
	}
	
	public static String format2(Date timestamp)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(timestamp);
	}
	
	public static String format3(Date timestamp)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("M‘¬d»’");
		return dateFormat.format(timestamp);
	}
	
	public static Date toDate(String dateString)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return  new Date(format.parse(dateString).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static int getDiffDays(Date earlyDate, Date lateDays) {
		int daySec = 24 * 60 * 60 * 1000;
		return (int) (lateDays.getTime() / daySec - earlyDate.getTime() / daySec);
	}
}
