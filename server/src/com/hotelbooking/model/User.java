package com.hotelbooking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.sf.json.JSONObject;

@Entity
@Table(name = "app_user")
public class User implements JsonObject{
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "account")
	private String account;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "name")
	private String name;
	
	public User() {
	}
	

	public User(String account, String password, String name) {
		super();
		this.account = account;
		this.password = password;
		this.name = name;
	}
	
	@Override
	public String getJsonName() {
		return "user";
	}


	@Override
	public String getJsonValue() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("name", name);
		return jsonObject.toString();
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
