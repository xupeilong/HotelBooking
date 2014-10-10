package com.hotelbooking.network.utils;

import org.apache.http.NameValuePair;

public class Parameter implements NameValuePair{

	private String name = null;
	private String value = null;
	
	public Parameter(String name, String value) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.value = value;
	}
	
	public Parameter(String name, int value) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.value = String.valueOf(value);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return value;
	}

}
