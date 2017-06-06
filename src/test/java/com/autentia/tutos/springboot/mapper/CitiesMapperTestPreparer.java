package com.autentia.tutos.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CitiesMapperTestPreparer {
    void mockCity(@Param("name") String name,@Param("country") String country,@Param("code") long code,
    		@Param("population") long population,@Param("founded") long founded);
}
