package com.hotelbooking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_house_info")
public class HouseInfo {
	
	@Id
	private int id;
	
	@Column(name = "house_id")
	private int houseId;
	
	@Column(name = "image_path")
	private String imagePath;
	
	public HouseInfo() {
	}
	
	public HouseInfo(int houseId, String imagePath) {
		this.houseId = houseId;
		this.imagePath = imagePath;
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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
}
