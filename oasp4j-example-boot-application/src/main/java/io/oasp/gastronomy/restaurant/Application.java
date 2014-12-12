package io.oasp.gastronomy.restaurant;

import io.oasp.gastronomy.restaurant.cxf.CxfConfigurator;
import io.oasp.gastronomy.restaurant.dozer.DozerConfigurator;
import io.oasp.gastronomy.restaurant.gui.GuiConfigurator;
import io.oasp.gastronomy.restaurant.jpa.DataaccessConfigurator;
import io.oasp.gastronomy.restaurant.logic.LogicConfigurator;
import io.oasp.gastronomy.restaurant.security.SecurityConfigurator;
import io.oasp.gastronomy.restaurant.service.ServiceConfigurator;

import org.apache.catalina.startup.Tomcat;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableAutoConfiguration
@ImportResource({ "classpath:config/app/beans-application.xml" })
@Import({DozerConfigurator.class, GuiConfigurator.class, LogicConfigurator.class, ServiceConfigurator.class,
		DataaccessConfigurator.class, SecurityConfigurator.class,
		CxfConfigurator.class })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {

		return new TomcatEmbeddedServletContainerFactory() {
			@Override
			protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
					Tomcat tomcat) {

				tomcat.addUser("chief", "chief");
				tomcat.addUser("waiter", "waiter");
				tomcat.addUser("barkeeper", "barkeeper");
				tomcat.addUser("cook", "cook");
				tomcat.addRole("chief", "Chief");
				tomcat.addRole("barkeeper", "Barkeeper");
				tomcat.addRole("cook", "Cook");
				tomcat.addRole("waiter", "Waiter");

				return super.getTomcatEmbeddedServletContainer(tomcat);
			}
		};
	}

	/*
	@Bean
	public ServletRegistrationBean dispatcherServlet() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
				new DispatcherServlet(), "/security/*");
		servletRegistrationBean.addInitParameter("contextConfigLocation",
				"classpath:config/app/gui/dispatcher-servlet.xml");
		servletRegistrationBean.setLoadOnStartup(1);
		return servletRegistrationBean;
	}
	*/

	@Bean
	public ServletRegistrationBean cxfReg() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
				new CXFServlet(), "/services/*");
		servletRegistrationBean.setLoadOnStartup(1);
		return servletRegistrationBean;
	}
}
