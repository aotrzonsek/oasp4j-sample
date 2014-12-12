package io.oasp.gastronomy.restaurant.cxf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import io.oasp.gastronomy.restaurant.general.service.impl.rest.SecurityRestServiceImpl;
import io.oasp.gastronomy.restaurant.offermanagement.service.impl.rest.OffermanagementRestServiceImpl;
import io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceImpl;
import io.oasp.gastronomy.restaurant.staffmanagement.service.impl.rest.StaffmanagementRestServiceImpl;
import io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest.TablemanagementRestServiceImpl;
import io.oasp.module.rest.service.impl.RestServiceExceptionFacade;
import org.apache.cxf.bus.spring.BusExtensionPostProcessor;
import org.apache.cxf.bus.spring.BusWiringBeanFactoryPostProcessor;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.inject.Inject;
import java.util.Arrays;

@Configuration
@ComponentScan(value = {"io.oasp.gastronomy.restaurant.*.service"})
public class CxfConfigurator {

    @Bean(destroyMethod = "shutdown")
    @Order(value = Integer.MIN_VALUE)
    public SpringBus cxf() {
        return new SpringBus();
    }

    @Bean
    public static BusWiringBeanFactoryPostProcessor busWiringBeanFactoryPostProcessor() {
        return new BusWiringBeanFactoryPostProcessor();
    }

    @Bean
    public BusExtensionPostProcessor busExtensionPostProcessor() {
        return new BusExtensionPostProcessor();
    }

    // TODO Jsr250BeanPostProcessor - is it necessary?

    @Bean
    @Inject
    public Server jaxRsServer(SecurityRestServiceImpl securityRestService,
            TablemanagementRestServiceImpl tablemanagementRestService,
            SalesmanagementRestServiceImpl salesmanagementRestService,
            OffermanagementRestServiceImpl offermanagementRestService,
            StaffmanagementRestServiceImpl staffmanagementRestService,
            ObjectMapper objectMapper, RestServiceExceptionFacade restServiceExceptionFacade) {

        JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        factory.setAddress("/rest");
        factory.setServiceBeans(
                Arrays.asList(securityRestService, tablemanagementRestService, salesmanagementRestService,
                        offermanagementRestService, staffmanagementRestService));
        factory.setProviders(Arrays.asList(new JacksonJsonProvider(objectMapper), restServiceExceptionFacade));
        return factory.create();
    }
}
