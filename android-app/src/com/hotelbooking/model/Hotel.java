package com.hotelbooking.model;

import java.util.List;

public class Hotel {

	private int id;
	private String name;
	private String area;
	private String level;
	private int price;
	private int distance;
	private String image_path;
	
	private String intro;
	private String address;
	private List<House> houses;
	
	// for hotel list
	public Hotel(int id, String name, String area, String level, int price, int distance, String image_path) {
		super();
		this.id = id;
		this.name = name;
		this.area = area;
		this.level = level;
		this.price = price;
		this.distance = distance;
		this.image_path = image_path;
	}
	
	// for hotel detail
	public Hotel(int id, String name, String level, String image_path, String intro, String area,
			String address, List<House> houses) {
		super();
		this.id = id;
		this.name = name;
		this.level = level;
		this.image_path = image_path;
		this.intro = intro;
		this.area = area;
		this.address = address;
		this.houses = houses;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<House> getHouses() {
		return houses;
	}

	public void setHouses(List<House> houses) {
		this.houses = houses;
	}
	
	
	
}
