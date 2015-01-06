package com.hotelbooking.dao;

import java.util.List;

import com.hotelbooking.model.City;

public class CityDAO extends BaseDAO{

	public List<City> getCities()
	{
		return listAll(City.class);
	}
}
