package io.oasp.gastronomy.restaurant.gui;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = { "io.oasp.gastronomy.restaurant.general.gui.api" })
public class GuiConfigurator {

	/*
	public UrlBasedViewResolver urlBasedViewResolver() {
		UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
		urlBasedViewResolver.setViewClass(JstlView.class);
		urlBasedViewResolver.setPrefix("/WEB-INF/gui/");
		urlBasedViewResolver.setSuffix(".jsp");

		return urlBasedViewResolver;
	}
	*/
}
