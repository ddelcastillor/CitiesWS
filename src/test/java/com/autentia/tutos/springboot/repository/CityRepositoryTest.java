package com.autentia.tutos.springboot.repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.autentia.tutos.springboot.mapper.CityMapper;
import com.autentia.tutos.springboot.repository.CityRepository;
import com.autentia.tutos.springboot.schema.cities.City;

public class CityRepositoryTest {
	private CityRepository sut;
	private CityMapper cityMapper;
	
	@Before
	public void init(){
		cityMapper = mock(CityMapper.class);
		sut = new CityRepository(cityMapper);
	}
	
	@Test
	public void findByCityShouldObtainCityNameByCityCode(){
		String expectedCity="Neuruppin";
		long code=16816;
		City city = new City();
		city.setName(expectedCity);
		
		when(cityMapper.getCityByCode(code)).thenReturn(city);
		City cityResponse = sut.findCityByCode(code);
		
		assertEquals(expectedCity,cityResponse.getName());
		verify(cityMapper).getCityByCode(code);
		
	}
	
	@Test
	public void addCity(){
		long expectedCode=777;
		
		City city = new City();
		city.setCode(expectedCode);
		city.setCountry("US");
		city.setName("NY");
		city.setPopulation(1700);
		city.setFounded(5000);

		Map<String, Object> cityMap = new HashMap<String, Object>();
		cityMap.put("code", city.getCode());
		cityMap.put("country", city.getCountry());
		cityMap.put("name", city.getName());
		cityMap.put("population", city.getPopulation());
		cityMap.put("founded", city.getFounded());
		
		when(cityMapper.insertCity(cityMap)).thenReturn(expectedCode);
		long code = sut.insertOrUpdateCity(city);
		
		assertEquals(expectedCode,code);
		verify(cityMapper).insertCity(cityMap);
	}
}
