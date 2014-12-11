package io.oasp.gastronomy.restaurant.service;

import javax.inject.Inject;

import io.oasp.gastronomy.restaurant.general.service.impl.rest.ApplicationObjectMapperFactory;
import io.oasp.module.rest.service.impl.RestServiceExceptionFacade;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ComponentScan(value = { "io.oasp.gastronomy.restaurant.*.service" })
public class ServiceConfigurator {

	@Value(value = "${security.expose.error.details}")
	private boolean exposeInternalErrorDetails;

	@Bean(name = "RestServiceExceptionFacade")
	public RestServiceExceptionFacade restServiceExceptionFacade() {
		RestServiceExceptionFacade restServiceExceptionFacade = new RestServiceExceptionFacade();
		restServiceExceptionFacade
				.setExposeInternalErrorDetails(exposeInternalErrorDetails);

		return restServiceExceptionFacade;
	}

	@Bean(name = "JacksonObjectMapper")
	@Inject
	public ObjectMapper jacksonObjectMapper(ApplicationObjectMapperFactory factory) {
		return factory.createInstance();
	}
}
