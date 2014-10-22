package com.hotelbooking.dao;

import com.hotelbooking.model.User;

public class UserDAO extends BaseDAO{
	
	public User getById(int userId)
	{
		return (User) getById(User.class, userId);
	}

	public boolean isAccountUsed(String account)
	{
		int num = countQuery("select count(*) from User where account = ?", account);
		return num > 0;
	}
	
	public boolean registerUser(User user)
	{
		return save(user);
	}
	
	public User login(String account, String password)
	{
		return (User) loadObject("from User where account = ? and password = ?", account, password);
	}
}
