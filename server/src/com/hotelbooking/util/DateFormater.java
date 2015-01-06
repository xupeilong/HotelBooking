package com.hotelbooking.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
	
	public static String format_refund(Date timestamp)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(timestamp);
	}
	
	public static String format_batch_no_part(Date timestamp)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
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
