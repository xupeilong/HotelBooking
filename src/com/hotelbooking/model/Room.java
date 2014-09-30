package com.hotelbooking.model;

import java.util.List;

public class Room {
	
	private String name;
	private List<String> features;
	private int price;
	
	
	public Room(String name, int price) {
		this.name = name;
		this.price = price;
	}

	
	public String getFeaturesString()
	{
		//for test
		return "ÎÞÔç ´ó´²";
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getFeatures() {
		return features;
	}
	public void setFeatures(List<String> features) {
		this.features = features;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}
