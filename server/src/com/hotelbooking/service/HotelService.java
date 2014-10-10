package com.hotelbooking.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hotelbooking.dao.HotelDAO;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.HotelInfo;

public class HotelService {
	public static String getHotelsJsonString(String type, int pageNum, int pageSize,
			int latitude, int longitude, int lowPrice, int hightPrice)
	{
		List<Hotel> hotels = null;
		switch (type) {
		case "default":
			hotels = getHotelsByDefault(pageNum, pageSize);
			break;
		default:
			break;
		}
		
		JSONArray jsonArray = new JSONArray();
		if (hotels != null)
			for (Hotel hotel: hotels)
			{
				HotelDAO hotelDAO = new HotelDAO();
				HotelInfo info = hotelDAO.getInfoByHotelId(hotel.getId());
				int id = hotel.getId();
				String name = hotel.getHotelName();
				String area = info.getArea();
				String level = info.getLevel();
				int price = hotelDAO.getLowestPrice(id);
				int distance = 100;
				String imagePath = info.getImagePath();
				JSONObject obj = new JSONObject();
				obj.put("id", id);
				obj.put("name", name);
				obj.put("area", area);
				obj.put("level", level);
				obj.put("price", price);
				obj.put("distance", distance);
				obj.put("image_path", imagePath);
				jsonArray.add(obj);
			}
		return jsonArray.toString();
	}
	
	private static List<Hotel> getHotelsByDefault(int pageNum, int pageSize)
	{
		HotelDAO hotelDAO = new HotelDAO();
		return hotelDAO.getHotelsByDefault(pageNum, pageSize);
	}
}
