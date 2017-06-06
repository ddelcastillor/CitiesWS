package com.autentia.tutos.springboot.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.autentia.tutos.springboot.repository.CityRepository;
import com.autentia.tutos.springboot.schema.cities.City;
import com.autentia.tutos.springboot.services.CityService;



public class CityServiceTest {
	private CityService sut;
	private CityRepository cityRepository;
	
	@Before
	public void init(){
		cityRepository = mock(CityRepository.class);
		sut= new CityService(cityRepository);
	}
	
	@Test
	public void getCityNameByCodeShouldReturnCorrectCityName(){
		long code=28850;
		String expectedCity="Torrejón de Ardoz";
		City city = new City();
		city.setName(expectedCity);
		
		when(cityRepository.findCityByCode(code)).thenReturn(city);
		City cityResponse = sut.getCityByCode(code);
		
		assertEquals(expectedCity,cityResponse.getName());
        verify(cityRepository).findCityByCode(code);
	}
	
	@Test
	public void addNewCity(){
		long expectedCode=888;
		City city = new City();
		
		city.setCode(expectedCode);
		city.setCountry("US");
		city.setName("NY");
		city.setPopulation(1700);
		city.setFounded(5000);
		
		when(cityRepository.insertOrUpdateCity(city)).thenReturn(expectedCode);
		long code = sut.addCity(city);
		
		assertEquals(expectedCode,code);
		verify(cityRepository).insertOrUpdateCity(city);
	}
	
}
