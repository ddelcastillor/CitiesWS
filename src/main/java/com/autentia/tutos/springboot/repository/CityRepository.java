package com.autentia.tutos.springboot.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.autentia.tutos.springboot.mapper.CityMapper;
import com.autentia.tutos.springboot.schema.cities.City;

@Repository
public class CityRepository {

	
	private CityMapper cityMapper;

	@Autowired
	public CityRepository(CityMapper cityMapper) {
		this.cityMapper = cityMapper;
	}

	public long insertOrUpdateCity(City city) {

		Map<String, Object> cityMap = new HashMap<String, Object>();
		cityMap.put("code", city.getCode());
		cityMap.put("country", city.getCountry());
		cityMap.put("name", city.getName());
		cityMap.put("population", city.getPopulation());
		cityMap.put("founded", city.getFounded());
		this.cityMapper.insertCity(cityMap);

		return city.getCode();

	}

	public City findCityByCode(long code) {
		return cityMapper.getCityByCode(code);
	}

}