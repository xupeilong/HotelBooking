package com.hotelbooking.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
	
	private static final String SP_FILE_NAME_HOTEL_BOOKING = "HOTEL_BOOKING_SP_FILE";
	private static final String SP_KEY_AUTO_LOGIN_STATUS = "AUTO_LOGIN_STATUS_SP_KEY";
	private static final String SP_KEY_ACCOUNT = "ACCOUNT_SP_KEY";
	private static final String SP_KEY_PASSWORD = "PASSWORD_SP_KEY";
	
	private Context context;
	
	public PreferenceHelper(Context context) {
		this.context = context;
	}
	
	public void saveAutoLoginStatus(boolean isAuto)
	{
		SharedPreferences setting = context.getSharedPreferences(SP_FILE_NAME_HOTEL_BOOKING, Context.MODE_WORLD_READABLE);
		setting.edit().putBoolean(SP_KEY_AUTO_LOGIN_STATUS, isAuto).commit();
	}
	
	public boolean getAutoLoginStatus()
	{
		SharedPreferences setting = context.getSharedPreferences(SP_FILE_NAME_HOTEL_BOOKING, Context.MODE_WORLD_READABLE);
		return setting.getBoolean(SP_KEY_AUTO_LOGIN_STATUS, false);
	}

	public void saveAccount(String account)
	{
		SharedPreferences setting = context.getSharedPreferences(SP_FILE_NAME_HOTEL_BOOKING, Context.MODE_WORLD_READABLE);
		setting.edit().putString(SP_KEY_ACCOUNT, account).commit();
	}
	
	public void savePassword(String password)
	{
		SharedPreferences setting = context.getSharedPreferences(SP_FILE_NAME_HOTEL_BOOKING, Context.MODE_WORLD_READABLE);
		setting.edit().putString(SP_KEY_PASSWORD, password).commit();
	}
	
	public String getAccount()
	{
		SharedPreferences setting = context.getSharedPreferences(SP_FILE_NAME_HOTEL_BOOKING, Context.MODE_WORLD_READABLE);
		return setting.getString(SP_KEY_ACCOUNT, null);
	}
	
	public String getPassword()
	{
		SharedPreferences setting = context.getSharedPreferences(SP_FILE_NAME_HOTEL_BOOKING, Context.MODE_WORLD_READABLE);
		return setting.getString(SP_KEY_PASSWORD, null);
	}
	
	public void resetAccountInfo()
	{
		saveAutoLoginStatus(false);
		saveAccount(null);
		savePassword(null);
	}
}

