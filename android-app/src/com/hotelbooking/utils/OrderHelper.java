package com.hotelbooking.utils;

import java.util.Date;

public class OrderHelper {

	
	public static String getOrderDateString(Date checkinDate, Date checkoutDate)
	{
		String result = "";
		int days = checkoutDate.getDay() - checkinDate.getDay();
		result = result + days + "晚  ";
		result = result + DateFormater.format2(checkinDate) + " - " + DateFormater.format2(checkoutDate);
		return result;
	}
	
	public static String getStatusString(int statusCode)
	{
		String statusString;
		switch (statusCode) {
		case 2:
			statusString = "待处理";
			break;
		case 1:
			statusString = "已发出预定请求";
			break;
		case 0:
			statusString = "预定成功";
			break;
		case 3:
			statusString = "已取消";
			break;
		case 5:
			statusString = "特殊订单";
			break;
		default:
			statusString = "未知";
			break;
		}
		return statusString;
	}
}
