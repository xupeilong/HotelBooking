package com.hotelbooking.dao;

import java.util.ArrayList;
import java.util.List;

import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.HotelInfo;
import com.hotelbooking.model.House;
import com.hotelbooking.model.HousePrice;

public class HotelDAO extends BaseDAO{
	
	public Hotel getHotelById(int hotelId)
	{
		Hotel hotel = (Hotel) getById(Hotel.class, hotelId);
		HotelInfo info = getHotelInfoByHotelId(hotel.getId());
		hotel.setInfo(info);
		return hotel;
		
	}
	
	public List<Hotel> getHotelsByDefault(int pageNum, int pageSize)
	{
		List<Hotel> hotels = listAll(Hotel.class, pageNum, pageSize);
		for (Hotel hotel: hotels)
		{
			HotelInfo info = getHotelInfoByHotelId(hotel.getId());
			hotel.setInfo(info);
			int lowPrice = getLowestPrice(hotel.getId());
			hotel.setLowPrice(lowPrice);
		}
		return hotels;
		
	}
	
	private HotelInfo getHotelInfoByHotelId(int hotelId)
	{
		HotelInfo info = (HotelInfo) loadObject("from HotelInfo where hotelId = " + hotelId);
		if (info == null)
			info = new HotelInfo("none", "none", "none");
		return info;
	}
	
	private int getLowestPrice(int hotelId)
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
