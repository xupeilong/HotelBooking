package com.hotelbooking.model;

import java.util.Date;

public class Order {

	public int id;
	
	private int statusCode;
	private int payMoney;
	private String hotelName;
	private String hotelAddress;
	private Date checkInDate;	
	private Date checkOutDate;
	private int houseCount;
	private String houseName;
	private String customerName;
	
	public Order() {
	}
	
	
	
	
	public Order(int id, int statusCode, int payMoney, String hotelName,
			String hotelAddress, Date checkInDate, Date checkOutDate,
			int houseCount, String houseName, String customerName) {
		super();
		this.id = id;
		this.statusCode = statusCode;
		this.payMoney = payMoney;
		this.hotelName = hotelName;
		this.hotelAddress = hotelAddress;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.houseCount = houseCount;
		this.houseName = houseName;
		this.customerName = customerName;
	}




	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public int getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(int payMoney) {
		this.payMoney = payMoney;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getHotelAddress() {
		return hotelAddress;
	}
	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}
	public Date getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public int getHouseCount() {
		return houseCount;
	}
	public void setHouseCount(int houseCount) {
		this.houseCount = houseCount;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
}
