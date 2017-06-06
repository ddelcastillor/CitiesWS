package com.autentia.tutos.springboot;
 
import java.util.List;
import java.util.Properties;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.SoapEnvelopeLoggingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import com.autentia.tutos.springboot.services.FaultExceptionService;


 
@EnableWs
@Configuration
public class ApplicationConfig extends WsConfigurerAdapter {
         
    private static final String SCHEMA_LOCATION = "schemas/cities.xsd";
    private static final String PORTTYPE_NAME = "Cities";
 
    @Bean
    public ServletRegistrationBean dispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(false);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }
 
    @Bean(name = "cities")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema getSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName(PORTTYPE_NAME); 
        wsdl11Definition.setLocationUri("/ws/cities");
        wsdl11Definition.setTargetNamespace("http://autentia.com/tutos/definitions");
        wsdl11Definition.setSchema(getSchema);
        wsdl11Definition.setCreateSoap12Binding(true);
        wsdl11Definition.setCreateSoap11Binding(false);
        return wsdl11Definition; 
    }       
 
    @Bean
    public SaajSoapMessageFactory messageFactory() {
        SaajSoapMessageFactory saajSoapMessageFactory = new SaajSoapMessageFactory();
        saajSoapMessageFactory.setSoapVersion(SoapVersion.SOAP_12);
        return saajSoapMessageFactory;
    }
    
    @Bean
    public XsdSchema getSchema() {
        return new SimpleXsdSchema(new ClassPathResource(SCHEMA_LOCATION));
    }
    
    @Bean
    public SoapFaultMappingExceptionResolver exceptionResolver(){
        SoapFaultMappingExceptionResolver exceptionResolver = new DetailSoapFaultDefinitionExceptionResolver();

        SoapFaultDefinition faultDefinition = new SoapFaultDefinition();
        faultDefinition.setFaultCode(SoapFaultDefinition.SERVER);
        exceptionResolver.setDefaultFault(faultDefinition);

        Properties errorMappings = new Properties();
        errorMappings.setProperty(Exception.class.getName(), SoapFaultDefinition.SERVER.toString());
        errorMappings.setProperty(FaultExceptionService.class.getName(), SoapFaultDefinition.SERVER.toString());
        exceptionResolver.setExceptionMappings(errorMappings);
        exceptionResolver.setOrder(1);
        return exceptionResolver;
    }
    
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        PayloadValidatingInterceptor validationInterceptor = new PayloadValidatingInterceptor();
        validationInterceptor.setXsdSchema(getSchema());
        validationInterceptor.setValidateRequest(true);
        validationInterceptor.setValidateResponse(true);
        interceptors.add(validationInterceptor);
        interceptors.add(new SoapEnvelopeLoggingInterceptor());
    }
 
}
