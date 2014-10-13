package com.hotelbooking.dao;

import java.util.List;

import com.hotelbooking.model.House;
import com.hotelbooking.model.HouseInfo;
import com.hotelbooking.model.HousePrice;

public class HouseDAO extends BaseDAO{

	public List<House> getHouseListByHotelId(int hotelId)
	{
		List<House> houses = query("from House where hotelId = " + hotelId);
		for (House house: houses)
		{
			HouseInfo info = (HouseInfo) loadObject("from HouseInfo where houseId = " + house.getId());
			if (info == null)
				info = new HouseInfo(house.getId(), "none");
			house.setInfo(info);
		}
		return houses;
	}
	
	public int getPrice(int houseId)
	{
		HousePrice price = (HousePrice) loadObject("from HousePrice where houseId = " + houseId);
		if (price != null)
			return price.getPrice();
		else
			return -1;
	}
}
