package com.hotelbooking.dao;

import com.hotelbooking.model.AliPayLog;

public class AliPayLogDAO extends BaseDAO{
	
	public void saveAliPayLog(AliPayLog log)
	{
		save(log);
	}
	
	public boolean isTradeNoExists(String tradeNo)
	{
		return loadObject("from AliPayLog where outTradeNo = ?", tradeNo) != null;
	}
	

}
