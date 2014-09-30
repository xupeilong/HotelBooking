package com.hotelbooking.model;

import java.util.List;

public class HotelInfo {
	
	private int commentCount;
	private float environment;
	private float equipment;
	private float service;
	private float health;
	
	private int opened;
	
	private List<String> pictures;
	private List<Room> rooms;
	
	public HotelInfo(int commentCount, float env, float eqp, float svc, float hlth, int opnd) {
		this.commentCount = commentCount;
		this.environment = env;
		this.equipment = eqp;
		this.service = svc;
		this.health = hlth;
		this.opened = opnd;
	}
	
	public int getCommentCount() {
		return commentCount;
	}
	public String getCommentCountString() {
		return String.valueOf(commentCount);
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public float getEnvironment() {
		return environment;
	}
	public String getEnvironmentString() {
		return String.valueOf(environment);
	}
	public void setEnvironment(float environment) {
		this.environment = environment;
	}
	public float getEquipment() {
		return equipment;
	}
	public String getEquipmentString() {
		return String.valueOf(equipment);
	}
	public void setEquipment(float equipment) {
		this.equipment = equipment;
	}
	public float getService() {
		return service;
	}
	public String getServiceString() {
		return String.valueOf(service);
	}
	public void setService(float service) {
		this.service = service;
	}
	public float getHealth() {
		return health;
	}
	public String getHealthString() {
		return String.valueOf(health);
	}
	public void setHealth(float health) {
		this.health = health;
	}
	public int getOpened() {
		return opened;
	}
	public String getOpenedString(){
		return String.valueOf(opened);
	}
	public void setOpened(int opened) {
		this.opened = opened;
	}
	public List<String> getPictures() {
		return pictures;
	}
	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}
	public List<Room> getRooms() {
		return rooms;
	}
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
}
