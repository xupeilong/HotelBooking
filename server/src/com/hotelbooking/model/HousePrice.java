package com.hotelbooking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orderprocess_housingprice")
public class HousePrice {

	@Id
	private int id;
	
	@Column(name = "housingTypeId_id")
	private int houseId;
	
	@Column(name = "prices")
	private int price;
	
	public HousePrice() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
}
