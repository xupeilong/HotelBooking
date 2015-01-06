package com.hotelbooking.service;

import java.sql.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hotelbooking.dao.HotelDAO;
import com.hotelbooking.dao.HouseDAO;
import com.hotelbooking.dao.OrderDAO;
import com.hotelbooking.dao.UserDAO;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.House;
import com.hotelbooking.model.OriginOrder;
import com.hotelbooking.model.OriginOrderSearch;
import com.hotelbooking.model.OriginProcess;
import com.hotelbooking.model.Result;
import com.hotelbooking.model.User;
import com.hotelbooking.model.UserOrder;
import com.hotelbooking.util.DateFormater;

public class OrderService {

	public static Result placeOrder(int userId, String name, int houseId, int houseNum,
			Date checkInDate, Date checkOutDate, String requestMessage, String tradeNo, double totalFee)
	{
		String orderSource = "安卓手机应用";
		Date nowDate = new Date(System.currentTimeMillis());
		String orderNumber = DateFormater.format1(nowDate);
		int stateCode = 2;
		
		
		
		HouseDAO houseDAO = new HouseDAO();
		House house = houseDAO.getHouseById(houseId);
		int payMoney = houseDAO.getHousePrice(houseId) * houseNum * DateFormater.getDiffDays(checkInDate, checkOutDate);
		
		HotelDAO hotelDAO = new HotelDAO();
		Hotel hotel = hotelDAO.getHotelById(house.getHotelId());
		
		Date orderDate = nowDate;
		UserDAO userDAO = new UserDAO();
		OriginOrder originOrder = new OriginOrder(houseId, orderSource, orderNumber,
				stateCode, houseNum, payMoney, orderDate, checkInDate, checkOutDate,
				name, requestMessage);
		OrderDAO orderDAO = new OrderDAO();
		if (orderDAO.placeOrder(originOrder, userId, tradeNo, totalFee))
		{
			OriginProcess originProcess = new OriginProcess(originOrder.getId());
			OriginOrderSearch originOrderSearch = new OriginOrderSearch(originOrder.getId(),
					originOrder.getOrderNumber(), hotel.getHotelName(), house.getName(), 
					name, checkInDate, checkOutDate, orderSource);
			orderDAO.saveProcess(originProcess);
			orderDAO.saveOriginOrderSearch(originOrderSearch);
			
			Result result = new Result(0, "下单成功");
			return result;
		}
		else
			return new Result(1, "下单失败");
		
		
	}
	
	public static String getOrderListJsonString(int userId, int pageNum, int pageSize)
	{
		OrderDAO orderDAO = new OrderDAO();
		HotelDAO hotelDAO = new HotelDAO();
		HouseDAO houseDAO = new HouseDAO();
		List<UserOrder> orders = orderDAO.getOrderListByUserId(userId, pageNum, pageSize);
		JSONObject resultObj = new JSONObject();
		int isLastCode = 0;
		if (orders.size() < pageSize)
			isLastCode = 1;
		resultObj.put("is_last_page", isLastCode);
		JSONArray array = new JSONArray();
		for (UserOrder userOrder: orders)
		{
			OriginOrder originOrder = userOrder.getOriginOrder();
			House house = houseDAO.getHouseById(originOrder.getHouseId());
			Hotel hotel = hotelDAO.getHotelById(house.getHotelId());
			JSONObject obj = new JSONObject();
			obj.put("id", userOrder.getId());
			obj.put("state_code", originOrder.getStateCode());
			obj.put("pay_money", originOrder.getPayMoney());
			obj.put("hotel_name", hotel.getHotelName());
			obj.put("address", hotel.getAddress());
			obj.put("checkin_date", DateFormater.format2(originOrder.getCheckInDate()));
			obj.put("checkout_date", DateFormater.format2(originOrder.getCheckOutDate()));
			obj.put("house_count", originOrder.getRoomNum());
			obj.put("house_name", house.getName());
			obj.put("customer_name", originOrder.getCustomerName());
			array.add(obj);
		}
		resultObj.put("orders", array);
		return resultObj.toString();
	}
}
