package io.oasp.gastronomy.restaurant.security;

import io.oasp.gastronomy.restaurant.general.common.impl.security.ApplicationAuthenticationProvider;
import io.oasp.module.security.common.impl.accesscontrol.AccessControlProviderImpl;
import io.oasp.module.security.common.impl.accesscontrol.AccessControlSchemaProviderImpl;

import java.util.Arrays;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.annotation.Jsr250Voter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

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


}
