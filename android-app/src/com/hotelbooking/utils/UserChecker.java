package com.hotelbooking.utils;

import com.hotelbooking.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class UserChecker {

	private Context context;
	private Intent intent;
	private Bundle originExtras; 
	
	public UserChecker(Context context) {
		this.context = context;
		intent = new Intent();
		originExtras = new Bundle();
	}
	
	public Intent getIntent()
	{
		return intent;
	}
	
	public void startActivityAfterLogin(Context packageContext, Class cls)
	{
		if (Const.currentUser != null)
			intent.setClass(packageContext, cls);
		else
		{
			intent.setClass(packageContext, LoginActivity.class);
			intent.putExtra("next_activity_class", cls);
		}
		context.startActivity(intent);
	}
}
