package com.hotelbooking;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.House;
import com.hotelbooking.network.utils.HttpDataHandler;
import com.hotelbooking.network.utils.HttpDataLoader;
import com.hotelbooking.network.utils.Parameter;
import com.hotelbooking.utils.Const;

public class HotelInfoDataLoader implements HttpDataHandler {

	private HotelDetailActivity hotelDetailActivity;
	private HttpDataLoader httpDataLoader;

	public HotelInfoDataLoader(HotelDetailActivity hotelDetailActivity) {
		this.hotelDetailActivity = hotelDetailActivity;
	}

	public void startGettingHotelInfo(int hotelId) {
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter("hotel_id", hotelId));
		httpDataLoader = new HttpDataLoader(Const.GetHotelDetailURL,
				parameters, this, 0);
		httpDataLoader.start();
	}

	@Override
	public void handle(String data, int requestCode) {
		Hotel hotel = null;
		List<House> houses = new ArrayList<House>();
		JSONTokener tokener = new JSONTokener(data);
		try {
			JSONObject obj = (JSONObject) tokener.nextValue();
			JSONArray houseArray = obj.getJSONArray("houses");
			for (int i = 0; i < houseArray.length(); i++) {
				JSONObject houseObj = houseArray.getJSONObject(i);
				House house = new House(houseObj.getInt("id"),
						houseObj.getString("name"),
						houseObj.getString("image_path"),
						houseObj.getInt("breakfast"), houseObj.getInt("bed"),
						houseObj.getInt("net"), houseObj.getInt("prepay"),
						houseObj.getInt("price"));
				houses.add(house);
			}

			hotel = new Hotel(obj.getInt("hotel_id"), obj.getString("name"),
					obj.getString("level"), obj.getString("icon_image_path"),
					obj.getString("intro"), obj.getString("area"), obj.getString("address"), houses);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hotelDetailActivity.onDataReady(hotel);
	}
}
