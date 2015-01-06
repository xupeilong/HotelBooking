package com.hotelbooking.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.hotelbooking.dao.BaseDAO;
import com.hotelbooking.dao.CityDAO;
import com.hotelbooking.dao.HotelDAO;
import com.hotelbooking.model.City;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.HotelInfo;

public class HotelService {
	public static String getCityListString()
	{
		CityDAO cityDAO = new CityDAO();
		List<City> cities =  cityDAO.getCities();
		JSONArray jsonArray = new JSONArray();
		for (City city: cities)
		{
			JSONObject obj = new JSONObject();
			obj.put("id", city.getId());
			obj.put("city_name", city.getCityName());
			obj.put("hotel_count", city.getHotelCount());
			jsonArray.add(obj);
		}
		return jsonArray.toString();
	}

	public static class HotelDistanceComparator implements Comparator<Hotel>
	{
		
		@Override
		public int compare(Hotel h1, Hotel h2) {
			if (h1.getDistance() < h2.getDistance())
				return -1;
			else if (h1.getDistance() == h2.getDistance())
				return 0;
			else
				return 1;
		}
	}
	
	
	private static final double EARTH_RADIUS = 6378.137;
	private static double rad(double d)
	{
	   return d * Math.PI / 180.0;
	}

	public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
	{
	   double radLat1 = rad(lat1);
	   double radLat2 = rad(lat2);
	   double a = radLat1 - radLat2;
	   double b = rad(lng1) - rad(lng2);
	   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
	    Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	   s = s * EARTH_RADIUS;
//	   s = Math.round(s * 10000) / 10000;
	   return s;
	}
	
	public static String getHotelsJsonString(int pageNum, int pageSize,
			int latitude, int longitude, String cityName, String keyword, int lowPrice, int highPrice)
	{
		HotelDAO hotelDAO = new HotelDAO();
		List<Hotel> hotels = hotelDAO.getHotels(cityName, keyword, lowPrice, highPrice);
		System.out.println("***** hotels: " + hotels.size());
		for (Hotel hotel: hotels)
		{
			if (latitude > 0 && longitude > 0)
			{
				HotelInfo info = hotel.getInfo();
				int distance = (int) ( 1000 * GetDistance((double)latitude / 1e6, (double)longitude / 1e6, (double) info.getLatitude() / 1e6, (double)info.getLongitude()/ 1e6));
				hotel.setDistance(distance);
			}
			else
				hotel.setDistance(-1);
		}
		Collections.sort(hotels, new HotelDistanceComparator());
		List<Hotel> res = new ArrayList<Hotel>();
		for (int i = pageSize * pageNum; i <  pageSize * (pageNum + 1); i++)
			if (i < hotels.size())
				res.add(hotels.get(i));	
			else
				break;
		JSONArray jsonArray = new JSONArray();
		for (Hotel hotel: res)
		{
			HotelInfo info = hotel.getInfo();
			
			int id = hotel.getId();
			String name = hotel.getHotelName();
			String city = hotel.getHotelCity();
			String area = info.getArea();
			String level = info.getLevel();
			int price = hotel.getLowPrice();
			int distance = hotel.getDistance();
			String imagePath = info.getImagePath();
			JSONObject obj = new JSONObject();
			int hotelLatitude = info.getLatitude();
			int hotelLongitude = info.getLongitude();
			obj.put("id", id);
			obj.put("name", name);
			obj.put("city", city);
			obj.put("area", area);
			obj.put("level", level);
			obj.put("price", price);
			obj.put("distance", distance);
			obj.put("latitude", hotelLatitude);
			obj.put("longitude", hotelLongitude);
			obj.put("image_path", imagePath);
			jsonArray.add(obj);
		}
		return jsonArray.toString();
	}
	
}
