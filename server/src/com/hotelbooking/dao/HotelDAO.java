package com.hotelbooking.dao;

import java.util.ArrayList;
import java.util.List;

import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.HotelInfo;
import com.hotelbooking.model.House;
import com.hotelbooking.model.HousePrice;

public class HotelDAO extends BaseDAO{
	
	public List<Hotel> getHotelsByDefault(int pageNum, int pageSize)
	{
		return listAll(Hotel.class, pageNum, pageSize);
	}
	
	public HotelInfo getInfoByHotelId(int hotelId)
	{
		return  (HotelInfo) loadObject("from HotelInfo where hotelId = " + hotelId);
	}
	
	public int getLowestPrice(int hotelId)
	{
		List<House> houses = query("from House where hotelId = " + hotelId);
		int min = -1;
		for (House house: houses)
		{
			int id = house.getId();
			HousePrice price = (HousePrice) loadObject("from HousePrice where houseId = " + id);
			if (min == -1 || price.getPrice() < min)
				min = price.getPrice();
		}
		return min;
	}
}
