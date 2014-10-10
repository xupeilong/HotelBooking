package com.hotelbooking.model;

public class Hotel {

	private int id;
	private String name;
	private String area;
	private String level;
	private int price;
	private int distance;
	private String image_path;
	
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
	
	
	
}