package com.hotelbooking.model;

public class City {

	private int id;
	private String cityName;
	private int hotelCount;
	
	public City() {
	}

	public City(int id, String cityName, int hotelCount) {
		super();
		this.id = id;
		this.cityName = cityName;
		this.hotelCount = hotelCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getHotelCount() {
		return hotelCount;
	}

	public void setHotelCount(int hotelCount) {
		this.hotelCount = hotelCount;
	}
	
	
}
