package com.hotelbooking.dao;

import java.util.ArrayList;
import java.util.List;

import com.hotelbooking.model.OriginOrder;
import com.hotelbooking.model.UserOrder;

public class OrderDAO extends BaseDAO{

	public boolean placeOrder(OriginOrder originOrder, int userId)
	{
		boolean result1 = save(originOrder);
		if (result1)
		{
			UserOrder userOrder = new UserOrder(userId, originOrder.getId());
			boolean result2 = save(userOrder);
			if (result2)
				return true;
			else
				delele(originOrder);			
		}
		return false;
	}
	
	public List<UserOrder> getOrderListByUserId(int userId, int pageNum, int pageSize)
	{
		
		List<UserOrder> orders = query(pageNum, pageSize, "from UserOrder where userId = ?", userId);
		for (UserOrder userOrder: orders)
		{
			OriginOrder originOrder = (OriginOrder) getById(OriginOrder.class, userOrder.getOriginOrderId());
			userOrder.setOriginOrder(originOrder);
		}
		return orders;
	}
}
