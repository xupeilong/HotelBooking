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
		Hotel hotel = new Hotel();
		hotel.setName("������Ƶ�");
		hotel.setScore((float) 5.0);
		hotel.setPrice(2999);
		hotel.setLevel("ţ���;Ƶ�");
		hotel.setDiscoutn(true);
		hotel.setArea("�����йش����");
		hotel.setDistance((float) 213);

		HotelInfo hotelInfo = new HotelInfo(1234, 4.5f, 4.4f, 4.4f, 4.6f, 2009);
		List<Room> rooms = new ArrayList<Room>();
		for (int i = 0; i < 5; i++)
		{
			Room room = new Room("��ͳ�׷�", 998);
			rooms.add(room);
		}
		hotelInfo.setRooms(rooms);
		hotelInfoActivity.onDataReady(hotel, hotelInfo);
	}
	

}
