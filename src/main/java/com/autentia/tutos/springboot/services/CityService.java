package com.autentia.tutos.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autentia.tutos.springboot.repository.CityRepository;
import com.autentia.tutos.springboot.schema.cities.City;
 
@Service
public class CityService{
    

    private CityRepository cityRepository;
    
	@Autowired    
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City getCityByCode(long code) {
        return cityRepository.findCityByCode(code);
    }
    
    public long deleteCityByCode(long code) {
        return cityRepository.deleteByCode(code);
    }
 

	public long addCity(City city) {
		return cityRepository.insertOrUpdateCity(city);
	}

}
