package com.hotelbooking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "orderprocess_hotel")
public class Hotel {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "hotelName")
	private String hotelName;
	
	@Column(name = "hotelCity")
	private String hotelCity;
	
	@Column(name = "address")
	private String address;
	
	@Transient
	private HotelInfo info;
	
	@Transient
	private int lowPrice;
	
	public Hotel() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelCity() {
		return hotelCity;
	}

	public void setHotelCity(String hotelCity) {
		this.hotelCity = hotelCity;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public HotelInfo getInfo() {
		return info;
	}

	public void setInfo(HotelInfo info) {
		this.info = info;
	}

	public int getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(int lowPrice) {
		this.lowPrice = lowPrice;
	}
	
}
