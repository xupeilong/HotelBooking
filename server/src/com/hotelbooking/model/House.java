package com.hotelbooking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orderprocess_housingtype")
public class House {
	
	@Id
	private int id;
	
	@Column(name = "roomName")
	private String name;
	
	@Column(name = "hotelId_id")
	private int hotelId;
	
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
	


}
