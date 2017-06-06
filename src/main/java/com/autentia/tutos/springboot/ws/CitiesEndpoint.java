package com.autentia.tutos.springboot.ws;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.autentia.tutos.springboot.schema.cities.AddCityRequest;
import com.autentia.tutos.springboot.schema.cities.AddCityResponse;
import com.autentia.tutos.springboot.schema.cities.City;
import com.autentia.tutos.springboot.schema.cities.GetCityByCodeRequest;
import com.autentia.tutos.springboot.schema.cities.GetCityByCodeResponse;
import com.autentia.tutos.springboot.services.CityService;
import com.autentia.tutos.springboot.services.FaultExceptionService;
import com.autentia.tutos.springboot.services.FaultService;

@Endpoint
public class CitiesEndpoint {

	static final Logger logger = LogManager.getLogger(CitiesEndpoint.class.getName());

	CityService cityService;

	@Autowired
	public CitiesEndpoint(CityService cityService) {
		this.cityService = cityService;
	}

	@PayloadRoot(namespace = "http://autentia.com/tutos/springboot/schema/cities", localPart = "getCityByCodeRequest")
	@ResponsePayload
	public GetCityByCodeResponse getCityByCode(@RequestPayload GetCityByCodeRequest request) {
		sanityGetCityByCodeRequest(request);
		GetCityByCodeResponse response = new GetCityByCodeResponse();

		City city = cityService.getCityByCode(request.getCode());
		if (city == null) {
			throw new FaultExceptionService("NOT_FOUND",
					new FaultService("001", "No hay una ciudad asignada a ese codigo."));
		}
		response.setCity(city);
		return response;
	}

	@PayloadRoot(namespace = "http://autentia.com/tutos/springboot/schema/cities", localPart = "addCityRequest")
	@ResponsePayload
	public AddCityResponse addCity(@RequestPayload AddCityRequest request) {
		AddCityResponse response = new AddCityResponse();
		sanityGetCityByCodeRequest(request);
		long code = cityService.addCity(request.getCity());
		response.setCode(code);
		return response;
	}

	private void sanityGetCityByCodeRequest(GetCityByCodeRequest request) {
		if (request.getCode() == 0) {
			throw new FaultExceptionService("ILLEGAL_ARGUMENT",
					new FaultService("005", "El codigo de la ciudad tiene que tener un valor numerico distinto de 0."));
		}
	}

	private void sanityGetCityByCodeRequest(AddCityRequest request) {
		if (request.getCity().getCode() == 0) {
			throw new FaultExceptionService("ILLEGAL_ARGUMENT",
					new FaultService("005", "El codigo de la ciudad tiene que tener un valor numerico distinto de 0."));
		} else {
			City city = cityService.getCityByCode(request.getCity().getCode());
			if (city != null) {
				throw new FaultExceptionService("ILLEGAL_ARGUMENT", new FaultService("003", "Ciudad ya existente."));
			}
		}

	}

}
