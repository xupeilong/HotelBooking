package com.hotelbooking.model;

public class Hotel {

	private String ID;
	private String name;
	private String iconURL;
	private Float score;
	private int price;
	private String level;
	private boolean isDiscount;
	private String area;
	private Float distance;
	
	private String address;

	
	
	
	public Hotel() {
	}

	
	public String getID() {
		return ID;
	}
	
	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getIconURL() {
		return iconURL;
	}


	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}


	public Float getScore() {
		return score;
	}
	
	public String getScoreString(){
		return String.valueOf(score);
	}


	public void setScore(Float score) {
		this.score = score;
	}


	public int getPrice() {
		return price;
	}
	
	public String getPriceString(){
		return String.valueOf(price);
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public boolean isDiscoutn() {
		return isDiscount;
	}


	public void setDiscoutn(boolean isDiscoutn) {
		this.isDiscount = isDiscoutn;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public Float getDistance() {
		return distance;
	}
	
	public String getDistanceString() {
		return String.valueOf(distance);
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}

	
	
}
