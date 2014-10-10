package com.hotelbooking;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.provider.SyncStateContract.Constants;

import com.hotelbooking.model.Hotel;
import com.hotelbooking.network.utils.HttpDataHandler;
import com.hotelbooking.network.utils.HttpDataLoader;
import com.hotelbooking.network.utils.Parameter;
import com.hotelbooking.utils.Const;


public class HotelListDataLoader implements HttpDataHandler{

	private HotelListActivity hotelListActivity;
	private HttpDataLoader httpDataLoader;
	
	public HotelListDataLoader(HotelListActivity hotelListActivity) {
		this.hotelListActivity = hotelListActivity;
	}
	
	public void startGettingHotelList(int page, int count)
	{
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter("type", "default"));
		parameters.add(new Parameter("page", page));
		parameters.add(new Parameter("count", count));
		httpDataLoader = new HttpDataLoader(Const.GetHotelListURL, parameters, this, 0);
		httpDataLoader.start();
	}

	@Override
	public void handle(String data, int requestCode) {

		List<Hotel> hotels = new ArrayList<Hotel>();
		JSONTokener tokener = new JSONTokener(data);
		JSONArray array;
		try {
			array = (JSONArray) tokener.nextValue();
			for (int i = 0; i < array.length(); i++)
			{
				JSONObject obj = array.getJSONObject(i);
				Hotel hotel = new Hotel(obj.getInt("id"),
						obj.getString("name"),
						obj.getString("area"),
						obj.getString("level"),
						obj.getInt("price"),
						obj.getInt("distance"),
						obj.getString("image_path"));
				hotels.add(hotel);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hotelListActivity.onHotelListDataReady(hotels);
	}
	

}
