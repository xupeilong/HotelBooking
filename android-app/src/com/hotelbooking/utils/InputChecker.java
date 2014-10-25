package com.hotelbooking.utils;

import android.content.Context;
import android.widget.Toast;

public class InputChecker {
	
	private Context context;
	
	public InputChecker(Context context) {
		this.context = context;
	}
	
	public boolean checkAccount(String account)
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
	
	public boolean checkPassword(String password)
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
	
	public boolean checkPassword(String password, String repeatPassword)
	{
		if (!password.equals(repeatPassword))
		{
			prompt("两次密码输入不一致");
			return false;
		}
		return checkPassword(password);
	}
	
	public boolean checkName(String name)
	{
		if (name == null || name.length() == 0)
		{
			prompt("请输入联系人姓名");
			return false;
		}
		
		if (name.length() < 2)
		{
			prompt("姓名过短");
			return false;
		}
		if (name.length() > 30)
		{
			prompt("姓名过长");
			return false;
		}
		return true;
	}
	
	private void prompt(String message)
	{
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
}
