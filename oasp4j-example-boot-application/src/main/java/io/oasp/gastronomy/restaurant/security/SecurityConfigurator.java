package io.oasp.gastronomy.restaurant.security;

import io.oasp.gastronomy.restaurant.general.service.impl.rest.ApplicationAccessDeniedHandler;
import io.oasp.module.security.common.impl.rest.JsonUsernamePasswordAuthenticationFilter;
import io.oasp.module.web.common.base.ToggleFilterWrapper;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.filter.RequestContextFilter;

@Configuration
@ComponentScan(value = { "io.oasp.gastronomy.restaurant.general.common.*.security" })
@Import({ CommonBeansConfigurator.class, MethodAccessDecisionManager.class,
		SecurityFilterConfigurator.class })
public class SecurityConfigurator extends WebSecurityConfigurerAdapter {

	@Inject
	private AuthenticationManager authenticationManager;

	@Inject
	private ApplicationAccessDeniedHandler accessDeniedHandler;

	@Inject
	private HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository;

	@Inject
	@Qualifier("FilterAccessDecisionManager")
	private UnanimousBased accessDecisionManager;

	@Value(value = "#{systemProperties['CsrfDisabled']}")
	private String disabledString;

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring()
		// All of Spring Security will ignore the requests
				.antMatchers("/jsclient/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		RequestContextFilter requestContextFilter = SecurityFilterConfigurator
				.requestContextFilter();
		SecurityContextPersistenceFilter securityContextPersistenceFilter = SecurityFilterConfigurator
				.securityContextPersistenceFilter();
		LogoutFilter restLogoutFilter = SecurityFilterConfigurator
				.restLogoutFilter();
		LogoutFilter logoutFilter = SecurityFilterConfigurator.logoutFilter();
		JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter = SecurityFilterConfigurator
				.jsonUsernamePasswordAuthenticationFilter(authenticationManager);
		RequestCacheAwareFilter requestCacheAwareFilter = SecurityFilterConfigurator
				.requestCacheAwareFilter();
		SecurityContextHolderAwareRequestFilter securityContextHolderAwareRequestFilter = SecurityFilterConfigurator
				.securityContextHolderAwareRequestFilter();
		AnonymousAuthenticationFilter anonymousAuthenticationFilter = SecurityFilterConfigurator
				.anonymousAuthenticationFilter();
		SessionManagementFilter sessionManagementFilter = SecurityFilterConfigurator
				.sessionManagementFilter();
		ExceptionTranslationFilter restExceptionTranslationFilter = SecurityFilterConfigurator
				.restExceptionTranslationFilter();
		CsrfFilter csrfFilter = SecurityFilterConfigurator.csrfFilter(
				httpSessionCsrfTokenRepository, accessDeniedHandler);
		ToggleFilterWrapper csrfFilterWrapper = SecurityFilterConfigurator
				.csrfFilterWrapper(csrfFilter, "True");
		ExceptionTranslationFilter exceptionTranslationFilter = SecurityFilterConfigurator
				.exceptionTranslationFilter();
		FilterSecurityInterceptor filterSecurityInterceptor = SecurityFilterConfigurator
				.filterSecurityInterceptor(authenticationManager,
						accessDecisionManager);

		http.requestMatchers()
				.antMatchers("/services/rest/login")
				.and()
				.addFilterAfter(requestContextFilter,
						WebAsyncManagerIntegrationFilter.class)
				.addFilterAfter(securityContextPersistenceFilter,
						RequestContextFilter.class)
				.addFilterAfter(restLogoutFilter,
						SecurityContextPersistenceFilter.class)
				.addFilterAfter(jsonUsernamePasswordAuthenticationFilter,
						LogoutFilter.class)
				.addFilterAfter(requestCacheAwareFilter,
						JsonUsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(securityContextHolderAwareRequestFilter,
						RequestCacheAwareFilter.class)
				.addFilterAfter(anonymousAuthenticationFilter,
						AnonymousAuthenticationFilter.class)
				.addFilterAfter(sessionManagementFilter,
						SessionManagementFilter.class)
				.addFilterAfter(restExceptionTranslationFilter,
						SessionManagementFilter.class)
				.addFilterAfter(filterSecurityInterceptor,
						ExceptionTranslationFilter.class);

		http.requestMatchers()
				.antMatchers("/services/rest/**")
				.and()
				.addFilterAfter(requestContextFilter,
						WebAsyncManagerIntegrationFilter.class)
				.addFilterAfter(securityContextPersistenceFilter,
						RequestContextFilter.class)
				.addFilterAfter(csrfFilterWrapper,
						SecurityContextPersistenceFilter.class)
				.addFilterAfter(restLogoutFilter, ToggleFilterWrapper.class)
				.addFilterAfter(jsonUsernamePasswordAuthenticationFilter,
						LogoutFilter.class)
				.addFilterAfter(requestCacheAwareFilter,
						JsonUsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(securityContextHolderAwareRequestFilter,
						RequestCacheAwareFilter.class)
				.addFilterAfter(anonymousAuthenticationFilter,
						AnonymousAuthenticationFilter.class)
				.addFilterAfter(sessionManagementFilter,
						SessionManagementFilter.class)
				.addFilterAfter(restExceptionTranslationFilter,
						SessionManagementFilter.class)
				.addFilterAfter(filterSecurityInterceptor,
						ExceptionTranslationFilter.class);

		http.requestMatchers()
				.antMatchers("/**")
				.and()
				.addFilterAfter(requestContextFilter,
						WebAsyncManagerIntegrationFilter.class)
				.addFilterAfter(securityContextPersistenceFilter,
						RequestContextFilter.class)
				.addFilterAfter(logoutFilter,
						SecurityContextPersistenceFilter.class)
				.addFilterAfter(jsonUsernamePasswordAuthenticationFilter,
						LogoutFilter.class)
				.addFilterAfter(requestCacheAwareFilter,
						JsonUsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(securityContextHolderAwareRequestFilter,
						RequestCacheAwareFilter.class)
				.addFilterAfter(anonymousAuthenticationFilter,
						AnonymousAuthenticationFilter.class)
				.addFilterAfter(sessionManagementFilter,
						SessionManagementFilter.class)
				.addFilterAfter(exceptionTranslationFilter,
						SessionManagementFilter.class)
				.addFilterAfter(filterSecurityInterceptor,
						ExceptionTranslationFilter.class);
	}

}
