package com.hotelbooking.utils;

import java.io.File;

import com.hotelbooking.model.User;

public class Const {

	private static final String BASE_URL = "http://118.123.16.137:8080/HotelBooking";
	
	//for test
//	private static final String BASE_URL = "http://192.168.3.203:8080/HotelBooking";
//	private static final String BASE_URL = "http://192.168.0.120:8080/HotelBooking";
//	private static final String BASE_URL = "http://192.168.1.102:8080/HotelBooking";
	
	public static final String GetHotelListURL = BASE_URL + "/GetHotelList";
	public static final String GetHotelDetailURL = BASE_URL + "/GetHotelDetail";
	public static final String GetImageURL = BASE_URL + "/GetImage";
	public static final String LoginURL = BASE_URL + "/Login";
	public static final String RegisterURL = BASE_URL + "/Register";
	public static final String GetOrderListURL = BASE_URL + "/GetOrderList";
	public static final String GetCityListURL = BASE_URL + "/GetCityList";
	public static final String CheckCodeURL = BASE_URL + "/CheckCode";
	
	
	
	
	public static final String PICTURE_DIR = "hotelbooking" + File.separator;
	
	
	public static final String AliPayNofityURL= "http://118.123.16.137:8080/HotelBooking" + "/AliPayNotify";
	
	// for test
//	public static final String AliPayNofityURL= "http://10.33.7.111:8080/HotelBooking/AliPayNotify";
	
	public static User currentUser = null;
	// for test
//	public static User currentUser = new User(1, "TEST");
	
	
	
	public static final String[] priceStringList = {"����۸�", "��300����", "��300-��450", "��450-��600", "��600-��1000", "��1000����"};
	public static final int[] priceLowList = {0, 0, 300, 450, 600, 1000};
	public static final int[] priceHighList = {0, 300, 450, 600, 1000, 1000000};
	
	public static final String[] countStringList = {"1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��"};
	public static final String[] messageStringList = {"��", "˫��", "����", "�߲�", "����"};
	public static final String[] payTypeStringList = {"֧������ҳ֧��", "֧�������֧��"};	
	
}

