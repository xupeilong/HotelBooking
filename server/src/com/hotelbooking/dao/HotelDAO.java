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
	
	public List<Hotel> getHotels(String cityName, String keyword, int lowPrice, int highPrice)
	{
		if (cityName == null || cityName.equals("nearby"))
			cityName = "";
		if (keyword == null)
			keyword = "";
		List<HotelInfo> infos = query("from HotelInfo where hotel.hotelName like ? and hotel.hotelCity like ?" ,
				"%" + keyword + "%", "%" + cityName + "%");
		System.out.println("***** db: " + cityName + " " + infos.size());
		List<Hotel> res = new ArrayList<Hotel>();
		for (HotelInfo info: infos)
		{
			if (lowPrice < highPrice)
			{
				int properPrice = getProperPrice(info.getHotel().getId(), lowPrice, highPrice);
				if (properPrice == -1)
					continue;
				info.getHotel().setLowPrice(properPrice);
			}
			else
				info.getHotel().setLowPrice(getLowestPrice(info.getHotel().getId()));
			info.getHotel().setInfo(info);
			res.add(info.getHotel());
		}
		return res;
	}
	
	private HotelInfo getHotelInfoByHotelId(int hotelId)
	{
		HotelInfo info = (HotelInfo) loadObject("from HotelInfo where hotel.id = " + hotelId);
		if (info == null)
			info = new HotelInfo("none", "none", "none");
		return info;
	}
	
	private int getProperPrice(int hotelId, int lowPrice, int highPrice)
	{
		List<House> houses = query("from House where hotelId = " + hotelId);
		int min = -1;
		for (House house: houses)
		{
			int id = house.getId();
			HousePrice price = (HousePrice) loadObject("from HousePrice where houseId = " + id);
			int p = price.getPrice();
			if ((p >= lowPrice && p <= highPrice) && (min == -1 || p < min))
				min = price.getPrice();
		}
		return min;
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
