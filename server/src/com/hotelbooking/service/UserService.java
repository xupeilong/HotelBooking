package com.hotelbooking.service;

import com.hotelbooking.dao.UserDAO;
import com.hotelbooking.model.Result;
import com.hotelbooking.model.User;

public class UserService {
	
	public static Result login(String account, String password)
	{
		UserDAO userDAO = new UserDAO();
		User user = userDAO.login(account, password);
		if (user == null)
			return new Result(1, "��¼ʧ�ܣ��˺Ż��������");
		else
		{
			Result result = new Result(0, "��¼�ɹ�");
			result.setDataObject(user);
			return result;
		}
	}

	public static Result register(String account, String password, String name)
	{
		if (!checkAccount(account) || !checkName(name))
			return new Result(2, "ʧ�ܣ���ʽ����");
		UserDAO userDAO = new UserDAO();
		if (userDAO.isAccountUsed(account))
			return new Result(1, "ʧ�ܣ����˻����ѱ�ע��");
		User user = new User(account, password, name);
		if (userDAO.registerUser(user))
		{
			Result result = new Result(0, "ע��ɹ�");
			result.setDataObject(user);
			return result;
		}
		else
			return new Result(3, "ʧ�ܣ����ݿ����");
	}
	
	private static boolean checkAccount(String account)
	{
		int len = account.length();
		if (len < 6 || len > 20)
			return false;
		for (char ch: account.toCharArray())
			if (!isDigitOrLetterOrUnderline(ch))
				return false;
		return true;
	}
	
	private static boolean isDigitOrLetterOrUnderline(char ch)
	{
		if (ch >= '0' && ch <= '9')
			return true;
		if (ch >= 'a' && ch <= 'z')
			return true;
		if (ch >= 'A' && ch <= 'Z')
			return true;
		if (ch == '_')
			return true;
		return false;
	}
	
	private static boolean checkName(String name)
	{
		int len = name.length();
		if (len < 2 || len > 30)
			return false;
		return true;
	}
}
