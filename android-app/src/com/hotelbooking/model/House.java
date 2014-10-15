package com.hotelbooking.model;

public class House {

	private int id;
	private String name;
	private String imagePath;
	private int breakfast;
	private int bed;
	private int net;
	private int prepay;
	private int price;
	
	public House(int id, String name, String imagePath, int breakfast, int bed,
			int net, int prepay, int price) {
		super();
		this.id = id;
		this.name = name;
		this.imagePath = imagePath;
		this.breakfast = breakfast;
		this.bed = bed;
		this.net = net;
		this.prepay = prepay;
		this.price = price;
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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
	
	
	
}
