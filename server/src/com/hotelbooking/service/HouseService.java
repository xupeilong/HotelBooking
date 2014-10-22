package com.hotelbooking.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hotelbooking.dao.HotelDAO;
import com.hotelbooking.dao.HouseDAO;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.HotelInfo;
import com.hotelbooking.model.House;

public class HouseService {
	public static String getHotelInfoJsonString(int hotelId)
	{
		HotelDAO hotelDAO = new HotelDAO();
		Hotel hotel = hotelDAO.getHotelById(hotelId);
		HotelInfo hotelInfo = hotel.getInfo();
		HouseDAO houseDAO = new HouseDAO();
		List<House> houses = houseDAO.getHouseListByHotelId(hotelId);
		JSONObject obj = new JSONObject();
		obj.put("hotel_id", hotel.getId());
		obj.put("name", hotel.getHotelName());
		obj.put("level", hotelInfo.getLevel());
		obj.put("icon_image_path", hotelInfo.getImagePath());
		obj.put("intro", "intro");
		obj.put("address", hotel.getAddress());
		obj.put("area", hotel.getInfo().getArea());
		JSONArray houseArray = new JSONArray();
		for (House house: houses)
		{
			int price = houseDAO.getHousePrice(house.getId()); 
			JSONObject houseObj = new JSONObject();
			houseObj.put("id", house.getId());
			houseObj.put("name", house.getName());
			houseObj.put("image_path", house.getInfo().getImagePath());
			houseObj.put("breakfast", house.getBreakfast());
			houseObj.put("bed", house.getBed());
			houseObj.put("net", house.getNet());
			houseObj.put("prepay", house.getPrepay());
			houseObj.put("price", price);
			houseArray.add(houseObj);
		}
		obj.put("houses", houseArray);
		return obj.toString();
	}
}
