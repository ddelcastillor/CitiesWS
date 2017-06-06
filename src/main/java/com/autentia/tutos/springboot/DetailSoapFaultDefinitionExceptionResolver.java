package com.autentia.tutos.springboot;

import javax.xml.namespace.QName;

import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import com.autentia.tutos.springboot.services.FaultExceptionService;
import com.autentia.tutos.springboot.services.FaultService;

public class DetailSoapFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {

	@Override
	protected SoapFaultDefinition getFaultDefinition(Object ep, Exception ex) {
		
		SoapFaultDefinition def = new SoapFaultDefinition();

        if (ex instanceof FaultExceptionService) {
        	FaultService serviceFault = ((FaultExceptionService) ex).getFaultService();
    		def.setFaultCode(new QName("http://autentia.com/tutos", serviceFault.getCode()));
    		def.setFaultStringOrReason(serviceFault.getDescription());
        }
        
		return def;
		
		
	}

}
