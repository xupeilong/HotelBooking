package com.hotelbooking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_city")
public class City {

	@Id
	private int id;
	
	@Column(name = "city_name")
	private String cityName;
	
	@Column(name = "hotel_count")
	private int hotelCount;
	
	public City() {
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
