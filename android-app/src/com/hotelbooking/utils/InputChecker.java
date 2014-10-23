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
			prompt("�������û���");
			return false;
		}
		
		if (account.length() < 6)
		{
			prompt("�û�������");
			return false;
		}
		if (account.length() > 20)
		{
			prompt("�û�������");
			return false;
		}
		return true;
	}
	
	private boolean checkPassword(String password)
	{
		if (password == null || password.length() == 0)
		{
			prompt("����������");
			return false;
		}
		
		if (password.length() < 6)
		{
			prompt("�������");
			return false;
		}
		if (password.length() > 20)
		{
			prompt("�������");
			return false;
		}
		return true;
	}
	
	private void prompt(String message)
	{
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
}
