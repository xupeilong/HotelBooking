package com.hotelbooking;

import java.util.ArrayList;
import java.util.List;

import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.HotelInfo;
import com.hotelbooking.model.Room;


public class HotelInfoDataLoader implements HttpDataHandler{

	private HotelInfoActivity hotelInfoActivity;
	private HttpDataLoader httpDataLoader;
	
	public HotelInfoDataLoader(HotelInfoActivity hotelInfoActivity) {
		this.hotelInfoActivity = hotelInfoActivity;
	}
	
	public void startGettingHotelInfo()
	{
//		List<Parameter> parameters = new ArrayList<Parameter>();
//		httpDataLoader = new HttpDataLoader("UUUUUUURL", parameters, this, 0);
//		httpDataLoader.start();
		handle(null, 0);
		
	}

	@Override
	public void handle(String data, int requestCode) {

		// for test
	}
	

}
