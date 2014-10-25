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
	
	public boolean checkPassword(String password)
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
	
	public boolean checkPassword(String password, String repeatPassword)
	{
		if (!password.equals(repeatPassword))
		{
			prompt("�����������벻һ��");
			return false;
		}
		return checkPassword(password);
	}
	
	public boolean checkName(String name)
	{
		if (name == null || name.length() == 0)
		{
			prompt("��������ϵ������");
			return false;
		}
		
		if (name.length() < 2)
		{
			prompt("��������");
			return false;
		}
		if (name.length() > 30)
		{
			prompt("��������");
			return false;
		}
		return true;
	}
	
	private void prompt(String message)
	{
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
}
