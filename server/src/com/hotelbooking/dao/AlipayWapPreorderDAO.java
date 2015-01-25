package com.hotelbooking.dao;

import com.hotelbooking.model.AlipayWapPreorder;

public class AlipayWapPreorderDAO extends BaseDAO{

	public void savePreorder(AlipayWapPreorder alipayWapPreorder)
	{
		save(alipayWapPreorder);
	}
	
	public AlipayWapPreorder getByOutTradeNo(String outTradeNo)
	{
		AlipayWapPreorder preorder = (AlipayWapPreorder) loadObject("from AlipayWapPreorder where outTradeNo = ?", outTradeNo);
		if (preorder != null)
			delele(preorder);
		return preorder;
	}
	
	
}
