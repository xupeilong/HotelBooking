package com.hotelbooking.utils;

import java.io.File;

import com.hotelbooking.model.User;

public class Const {

//	private static final String BASE_URL = "http://118.123.16.137:8080/HotelBooking";
	
	//for test
	private static final String BASE_URL = "http://192.168.3.203:8080/HotelBooking";
	
	public static final String GetHotelListURL = BASE_URL + "/GetHotelList";
	public static final String GetHotelDetailURL = BASE_URL + "/GetHotelDetail";
	public static final String GetImageURL = BASE_URL + "/GetImage";
	public static final String LoginURL = BASE_URL + "/Login";
	public static final String RegisterURL = BASE_URL + "/Register";
	public static final String GetOrderListURL = BASE_URL + "/GetOrderList";
	
	
	public static final String PICTURE_DIR = "hotelbooking" + File.separator;
	
	
//	public static final String AliPayNofityURL= "http://118.123.16.137:8080/HotelBooking" + "/AliPayNotify";
	
	// for test
	public static final String AliPayNofityURL= "http://118.123.16.137:8080/HotelBookingPayNotify/AliPayNotify";
	
//	public static User currentUser = null;
	// for test
	public static User currentUser = new User(8, "11");
	
	
}

