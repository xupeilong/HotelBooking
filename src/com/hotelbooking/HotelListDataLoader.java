package com.hotelbooking;

import java.util.ArrayList;
import java.util.List;

import com.hotelbooking.model.Hotel;


public class HotelListDataLoader implements HttpDataHandler{

	private HotelListActivity hotelListActivity;
	private HttpDataLoader httpDataLoader;
	
	public HotelListDataLoader(HotelListActivity hotelListActivity) {
		this.hotelListActivity = hotelListActivity;
	}
	
	public void startGettingHotelList()
	{
//		List<Parameter> parameters = new ArrayList<Parameter>();
//		httpDataLoader = new HttpDataLoader("UUUUUUURL", parameters, this, 0);
//		httpDataLoader.start();
		handle(null, 0);
		
	}

	@Override
	public void handle(String data, int requestCode) {

		//for test
		List<Hotel> hotels = new ArrayList<Hotel>();
		for (int i = 0; i < 10; i++)
		{
			Hotel hotel = new Hotel();
			hotel.setName("������Ƶ�");
			hotel.setScore((float) 5.0);
			hotel.setPrice(2999);
			hotel.setLevel("ţ���;Ƶ�");
			hotel.setDiscoutn(true);
			hotel.setArea("�����йش����");
			hotel.setDistance((float) 213);
			hotels.add(hotel);
		}
		hotelListActivity.onHotelListDataReady(hotels);
	}
	

}
