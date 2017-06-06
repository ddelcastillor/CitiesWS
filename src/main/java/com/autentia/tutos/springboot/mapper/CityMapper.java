package com.autentia.tutos.springboot.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.autentia.tutos.springboot.schema.cities.City;


@Mapper
public interface CityMapper {

	long insertCity(Map<String, Object> cityMap);

	City getCityByCode(@Param("code") long code);

}
