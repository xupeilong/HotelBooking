package com.hotelbooking.dao;

import com.hotelbooking.model.User;

public class UserDAO extends BaseDAO{

	public int registerUser(User user)
	{
		save(user);
		return 0;
	}
}
