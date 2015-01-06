package com.hotelbooking.network;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.widget.Toast;

import com.hotelbooking.HotelListActivity;
import com.hotelbooking.MainActivity;
import com.hotelbooking.model.City;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.network.utils.HttpDataHandler;
import com.hotelbooking.network.utils.HttpDataLoader;
import com.hotelbooking.utils.Const;

public class SearchDataLoader implements HttpDataHandler{

	private MainActivity mainActivity = null;
	private HotelListActivity hotelListActivity = null;
	private HttpDataLoader httpDataLoader;
	
	public SearchDataLoader(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}
	
	public SearchDataLoader(HotelListActivity hotelListActivity) {
		this.hotelListActivity = hotelListActivity;
	}
	

	public void startGettingCityList()
	{
		httpDataLoader = new HttpDataLoader(Const.GetCityListURL, null, this, 1);
		httpDataLoader.start();
	}
	
	@Override
	public void handle(String data, int requestCode) {
		if (data == null)
		{
			Toast.makeText(mainActivity, "ÍøÂç´íÎó£¬Çë¼ì²éÍøÂç", Toast.LENGTH_LONG).show();
			mainActivity.onGotCityList(null);
			return;
		}
		
		List<City> cities = new ArrayList<City>();
		JSONTokener tokener = new JSONTokener(data);
		JSONArray array;
		try {
			array = (JSONArray) tokener.nextValue();
			for (int i = 0; i < array.length(); i++)
			{
				JSONObject obj = array.getJSONObject(i);
				City city = new City(obj.getInt("id"),
						obj.getString("city_name"), 
						obj.getInt("hotel_count"));
				cities.add(city);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mainActivity != null)
			mainActivity.onGotCityList(cities);
		else
			hotelListActivity.onGotCityList(cities);
		
	}

}
