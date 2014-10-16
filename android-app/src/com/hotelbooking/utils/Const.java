package com.hotelbooking.utils;

import java.io.File;

public class Const {

	private static final String BASE_URL = "http://192.168.3.225:8080/HotelBooking";
	
	public static final String GetHotelListURL = BASE_URL + "/GetHotelList";
	public static final String GetHotelDetailURL = BASE_URL + "/GetHotelDetail";
	public static final String GetImageURL = BASE_URL + "/GetImage";
	public static final String LoginURL = BASE_URL + "/Login";
	public static final String RegisterURL = BASE_URL + "/Register";
	
	public static final String PICTURE_DIR = "hotelbooking" + File.separator;
}

