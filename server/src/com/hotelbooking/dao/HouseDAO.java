package com.hotelbooking.dao;

import java.util.List;

import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.House;
import com.hotelbooking.model.HouseInfo;
import com.hotelbooking.model.HousePrice;

public class HouseDAO extends BaseDAO{

	public List<House> getHouseListByHotelId(int hotelId)
	{
		List<House> houses = query("from House where hotelId = ?", hotelId);
		for (House house: houses)
		{
			HouseInfo info = getHouseInfoByHouseId(house.getId());
			house.setInfo(info);
		}
		return houses;
	}
	
	public int getHousePrice(int houseId)
	{
		HousePrice price = (HousePrice) loadObject("from HousePrice where houseId = " + houseId);
		if (price != null)
			return price.getPrice();
		else
			return -1;
	}
	
	public House getHouseById(int houseId)
	{
		House house = (House) getById(House.class, houseId);
		HouseInfo info = getHouseInfoByHouseId(house.getId());
		house.setInfo(info);
		return house;
	}
	
	private HouseInfo getHouseInfoByHouseId(int houseId)
	{
		HouseInfo info = (HouseInfo) loadObject("from HouseInfo where houseId = " + houseId);
		if (info == null)
			info = new HouseInfo(houseId, "none");
		return info;
	}
}
