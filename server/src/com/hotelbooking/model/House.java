package com.hotelbooking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "orderprocess_housingtype")
public class House {
	
	@Id
	private int id;
	
	@Column(name = "roomName")
	private String name;
	
	@Column(name = "hotelId_id")
	private int hotelId;
	
	@Column(name = "roomBreakfast")
	private int breakfast;
	
	@Column(name = "roomBed")
	private int bed;
	
	@Column(name = "roomBroadband")
	private int net;
	
	@Column(name = "roomPrepay")
	private int prepay;
	
	@Transient
	private HouseInfo info;
	
	public House() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public HouseInfo getInfo() {
		return info;
	}

	public void setInfo(HouseInfo info) {
		this.info = info;
	}

	public int getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(int breakfast) {
		this.breakfast = breakfast;
	}

	public int getBed() {
		return bed;
	}

	public void setBed(int bed) {
		this.bed = bed;
	}

	public int getNet() {
		return net;
	}

	public void setNet(int net) {
		this.net = net;
	}

	public int getPrepay() {
		return prepay;
	}

	public void setPrepay(int prepay) {
		this.prepay = prepay;
	}
	
	

}
