package com.hotelbooking.utils;

import android.content.Context;
import android.widget.Toast;

public class InputChecker {
	
	private Context context;
	
	public InputChecker(Context context) {
		this.context = context;
	}
	
	private boolean checkAccount(String account)
	{
		if (account == null || account.length() == 0)
		{
			prompt("请输入用户名");
			return false;
		}
		
		if (account.length() < 6)
		{
			prompt("用户名过短");
			return false;
		}
		if (account.length() > 20)
		{
			prompt("用户名过长");
			return false;
		}
		return true;
	}
	
	private boolean checkPassword(String password)
	{
		if (password == null || password.length() == 0)
		{
			prompt("请输入密码");
			return false;
		}
		
		if (password.length() < 6)
		{
			prompt("密码过短");
			return false;
		}
		if (password.length() > 20)
		{
			prompt("密码过长");
			return false;
		}
		return true;
	}
	
	private void prompt(String message)
	{
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
}
