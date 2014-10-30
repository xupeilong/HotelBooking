package com.hotelbooking.dao;

import com.hotelbooking.model.AliPayLog;

public class AliPayLogDAO extends BaseDAO{
	
	public void saveAliPayLog(AliPayLog log)
	{
		save(log);
	}

}
