package com.autentia.tutos.springboot.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.autentia.tutos.springboot.Application;
import com.autentia.tutos.springboot.mapper.CityMapper;
import com.autentia.tutos.springboot.schema.cities.City;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class CityMapperIT {
	@Autowired
	private CityMapper sut;
	
	@Autowired
	private CitiesMapperTestPreparer preparer;
	
	@Ignore
	@Test
	public void getCityByCodeShouldReturnCorrectCityName() {
		

		String name="Bangkok",country="Tailand",expectedCityName="Bangkok";
		long code=78000,population=8000000,founded=1700;

		preparer.mockCity(name, country, code, population, founded);
		
		City city = sut.getCityByCode(code);		
		assertEquals(expectedCityName,city.getName());

	}
	
}
