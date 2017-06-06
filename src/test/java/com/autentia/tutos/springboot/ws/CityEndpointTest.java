package com.autentia.tutos.springboot.ws;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.autentia.tutos.springboot.schema.cities.AddCityRequest;
import com.autentia.tutos.springboot.schema.cities.AddCityResponse;
import com.autentia.tutos.springboot.schema.cities.City;
import com.autentia.tutos.springboot.schema.cities.GetCityByCodeRequest;
import com.autentia.tutos.springboot.schema.cities.GetCityByCodeResponse;
import com.autentia.tutos.springboot.services.CityService;
import com.autentia.tutos.springboot.services.FaultExceptionService;

public class CityEndpointTest {

	private CityService cityService;

	private CitiesEndpoint sut;

	@Before
	public void init() {
		cityService = mock(CityService.class);
		sut = new CitiesEndpoint(cityService);
	}

	@Test
	public void getCityByCodeShouldReturnCorrectCityName() {
		long code = 28850;
		String expectedCity = "Torrejón de Ardoz";
		GetCityByCodeRequest request = new GetCityByCodeRequest();
		request.setCode(code);
		City city = new City();
		city.setName(expectedCity);

		when(cityService.getCityByCode(code)).thenReturn(city);
		GetCityByCodeResponse response = sut.getCityByCode(request);
		City cityResponse = response.getCity();

		assertEquals(expectedCity, cityResponse.getName());
		verify(cityService).getCityByCode(code);

	}

	@Test
	public void addCity() {
		long expectedCityCode = 999, cityCodeResponse;

		City city = new City();

		city.setCode(expectedCityCode);
		city.setCountry("US");
		city.setName("NY");
		city.setPopulation(1700);
		city.setFounded(5000);
		AddCityRequest request = new AddCityRequest();
		request.setCity(city);

		when(cityService.addCity(city)).thenReturn(expectedCityCode);
		AddCityResponse response = sut.addCity(request);
		cityCodeResponse = response.getCode();

		assertEquals(expectedCityCode, cityCodeResponse);
		verify(cityService).addCity(city);

	}

	@Test(expected = FaultExceptionService.class)
	public void getCityByCodeShouldReturnNotFoundException() {
		long code = 666;
		GetCityByCodeRequest request = new GetCityByCodeRequest();
		request.setCode(code);

		GetCityByCodeResponse response = sut.getCityByCode(request);
		response.getCity();

		verify(cityService, Mockito.times(0)).getCityByCode(code);

	}

	@Test(expected = FaultExceptionService.class)
	public void getCityByCodeShouldReturnIllegalArgumentException() {
		long code = 0;
		GetCityByCodeRequest request = new GetCityByCodeRequest();
		request.setCode(code);

		GetCityByCodeResponse response = sut.getCityByCode(request);
		response.getCity();

		verify(cityService, Mockito.times(0)).getCityByCode(code);

	}

}
