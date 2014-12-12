package io.oasp.gastronomy.restaurant.security;

import io.oasp.gastronomy.restaurant.general.common.impl.security.ApplicationAuthenticationProvider;
import io.oasp.module.logging.common.impl.DiagnosticContextFacadeImpl;
import io.oasp.module.security.common.impl.accesscontrol.AccessControlProviderImpl;
import io.oasp.module.security.common.impl.accesscontrol.AccessControlSchemaProviderImpl;

import java.util.Arrays;

import javax.inject.Inject;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.annotation.Jsr250Voter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
public class CommonBeansConfigurator {
	@Bean(name = "AccessControlProvider")
	public AccessControlProviderImpl accessControlProviderImpl() {
		return new AccessControlProviderImpl();
	}

	@Bean(name = "AccessControlSchemaProvider")
	public AccessControlSchemaProviderImpl accessControlSchemaProviderImpl() {
		return new AccessControlSchemaProviderImpl();
	}

	@Bean(name = "FilterAccessDecisionManager")
	public UnanimousBased filterAccessDecisionManager() {
		return new UnanimousBased(
				Arrays.<AccessDecisionVoter> asList(new WebExpressionVoter()));
	}

	@Bean(name = "AuthenticationManager")
	@Inject
	public ProviderManager authenticationManager(
			ApplicationAuthenticationProvider applicationAuthenticationProvider) {
		return new ProviderManager(
				Arrays.<AuthenticationProvider> asList(applicationAuthenticationProvider));
	}

	@Bean
	public HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository() {
		return new HttpSessionCsrfTokenRepository();
	}

	@Bean(name = "MethodAccessDecisionManager")
	public UnanimousBased methodAccessDecisionManager() {
		return new UnanimousBased(
				Arrays.<AccessDecisionVoter> asList(new Jsr250Voter()));
	}

	@Bean(name = "DiagnosticContextFacade")
	public DiagnosticContextFacadeImpl diagnosticContextFacade() {
		return new DiagnosticContextFacadeImpl();
	}

	@Bean(name = "validator")
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	@Bean(name = "propertyConfigurer")
	public static PropertyPlaceholderConfigurer propertyConfigurer() {
		DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
		PropertyPlaceholderConfigurer propertyConfigurer = new PropertyPlaceholderConfigurer();
		propertyConfigurer
				.setLocations(
						resourceLoader
								.getResource("classpath:/config/app/application-default.properties"),
						resourceLoader
								.getResource("classpath:/config/env/application.properties"));
		return propertyConfigurer;
	}

}
