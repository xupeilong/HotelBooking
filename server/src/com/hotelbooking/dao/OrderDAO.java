package com.hotelbooking.dao;

import java.util.ArrayList;
import java.util.List;

import com.hotelbooking.model.OriginOrder;
import com.hotelbooking.model.OriginOrderSearch;
import com.hotelbooking.model.OriginProcess;
import com.hotelbooking.model.UserOrder;

public class OrderDAO extends BaseDAO{

	public boolean placeOrder(OriginOrder originOrder, int userId, String tradeNo, double totalFee)
	{
		boolean result1 = save(originOrder);
		if (result1)
		{
			System.out.println("****** fee " + totalFee);
			UserOrder userOrder = new UserOrder(userId, originOrder.getId(), tradeNo, totalFee);
			boolean result2 = save(userOrder);
			if (result2)
				return true;
			else
				delele(originOrder);			
		}
		return false;
	}
	
	public void saveProcess(OriginProcess process)
	{
		save(process);
	}
	
	public void saveOriginOrderSearch(OriginOrderSearch originOrderSearch)
	{
		save(originOrderSearch);
	}
	
	public UserOrder getUserOdrByPayNo(String payNo)
	{
		UserOrder userOrder = (UserOrder) loadObject("from UserOrder where ali_pay_trade_no = ?", payNo);
		OriginOrder originOrder = (OriginOrder) getById(OriginOrder.class, userOrder.getOriginOrderId());
		userOrder.setOriginOrder(originOrder);
		return userOrder;
	}
	
	public UserOrder getUserOdrByOriginOrdId(int orderId)
	{
		return (UserOrder) loadObject("from UserOrder where originOrderId = ?", orderId);
	}
	
	public List<UserOrder> getOrderListByUserId(int userId, int pageNum, int pageSize)
	{
		
		List<UserOrder> orders = query(pageNum, pageSize, "from UserOrder where userId = ? order by ali_pay_trade_no desc", userId);
		for (UserOrder userOrder: orders)
		{
			OriginOrder originOrder = (OriginOrder) getById(OriginOrder.class, userOrder.getOriginOrderId());
			userOrder.setOriginOrder(originOrder);
		}
		return orders;
	}
}
