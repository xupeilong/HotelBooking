package com.hotelbooking.utils;

import java.io.File;

public class Const {

	private static final String BASE_URL = "http://192.168.3.234:8080/HotelBooking";
	
	public static final String GetHotelListURL = BASE_URL + "/GetHotelList";
	public static final String GetImageURL = BASE_URL + "/GetImage";
	
	
	public static final String PICTURE_DIR = "hotelbooking" + File.separator;
}
