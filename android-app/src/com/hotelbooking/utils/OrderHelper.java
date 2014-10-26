package com.hotelbooking.utils;

import java.util.Date;

public class OrderHelper {

	
	public static String getOrderDateString(Date checkinDate, Date checkoutDate)
	{
		String result = "";
		int days = checkoutDate.getDay() - checkinDate.getDay();
		result = result + days + "��  ";
		result = result + DateFormater.format2(checkinDate) + " - " + DateFormater.format2(checkoutDate);
		return result;
	}
	
	public static String getStatusString(int statusCode)
	{
		String statusString;
		switch (statusCode) {
		case 2:
			statusString = "������";
			break;
		case 1:
			statusString = "�ѷ���Ԥ������";
			break;
		case 0:
			statusString = "Ԥ���ɹ�";
			break;
		case 3:
			statusString = "��ȡ��";
			break;
		case 5:
			statusString = "���ⶩ��";
			break;
		default:
			statusString = "δ֪";
			break;
		}
		return statusString;
	}
}
