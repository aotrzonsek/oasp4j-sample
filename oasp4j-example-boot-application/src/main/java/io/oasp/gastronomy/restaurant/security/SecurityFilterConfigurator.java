package io.oasp.gastronomy.restaurant.security;

import io.oasp.gastronomy.restaurant.general.gui.api.LoginControllerDefaultTargetUrl;
import io.oasp.gastronomy.restaurant.general.service.impl.rest.ApplicationAccessDeniedHandler;
import io.oasp.module.security.common.impl.rest.AuthenticationSuccessHandlerSendingOkHttpStatusCode;
import io.oasp.module.security.common.impl.rest.JsonUsernamePasswordAuthenticationFilter;
import io.oasp.module.security.common.impl.rest.LogoutSuccessHandlerReturningOkHttpStatusCode;
import io.oasp.module.security.common.impl.web.RetainAnchorFilter;
import io.oasp.module.web.common.base.ToggleFilterWrapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.ExpressionBasedFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.RequestContextFilter;

@Configuration
public class SecurityFilterConfigurator {
	
	@Bean
	public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
		return new BeanFactoryPostProcessor() {
			@Override
			public void postProcessBeanFactory(
					ConfigurableListableBeanFactory beanFactory)
					throws BeansException {
				System.out.println("");
				// BeanDefinitionRegistry.class.cast(beanFactory).removeBeanDefinition("JsonUsernamePasswordAuthenticationFilter");
			}
		};
	}

	public static FilterSecurityInterceptor filterSecurityInterceptor(AuthenticationManager authenticationManager,UnanimousBased accessDecisionManager) {
		FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
		filterSecurityInterceptor
				.setAuthenticationManager(authenticationManager);
		filterSecurityInterceptor
				.setAccessDecisionManager(accessDecisionManager);
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> map = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
		map.put(new AntPathRequestMatcher("/"), Arrays
				.<ConfigAttribute> asList(new SecurityConfig("isAnonymous()")));
		map.put(new AntPathRequestMatcher("/index.jsp"), Arrays
				.<ConfigAttribute> asList(new SecurityConfig("isAnonymous()")));
		map.put(new AntPathRequestMatcher("/security/login*"), Arrays
				.<ConfigAttribute> asList(new SecurityConfig("isAnonymous()")));
		map.put(new AntPathRequestMatcher("/j_spring_security_login*"), Arrays
				.<ConfigAttribute> asList(new SecurityConfig("isAnonymous()")));
		map.put(new AntPathRequestMatcher("/j_spring_security_logout*"), Arrays
				.<ConfigAttribute> asList(new SecurityConfig("isAnonymous()")));
		map.put(new AntPathRequestMatcher(
				"/services/rest/security/currentuser/"), Arrays
				.<ConfigAttribute> asList(new SecurityConfig(
						"isAnonymous() or isAuthenticated()")));
		map.put(new AntPathRequestMatcher("/**"), Arrays
				.<ConfigAttribute> asList(new SecurityConfig(
						"isAuthenticated()")));
		ExpressionBasedFilterInvocationSecurityMetadataSource ms = new ExpressionBasedFilterInvocationSecurityMetadataSource(
				map, new DefaultWebSecurityExpressionHandler());
		filterSecurityInterceptor.setSecurityMetadataSource(ms);

		return filterSecurityInterceptor;
	}

	public static SecurityContextPersistenceFilter securityContextPersistenceFilter() {
		return new SecurityContextPersistenceFilter();
	}

	public static SecurityContextHolderAwareRequestFilter securityContextHolderAwareRequestFilter() {
		SecurityContextHolderAwareRequestFilter securityContextHolderAwareRequestFilter = new SecurityContextHolderAwareRequestFilter();
		try {
			securityContextHolderAwareRequestFilter.afterPropertiesSet();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		
		return securityContextHolderAwareRequestFilter;
	}

	public static AnonymousAuthenticationFilter anonymousAuthenticationFilter() {
		return new AnonymousAuthenticationFilter("BF93JFJ091N00Q7HF");
	}

	public static RequestCacheAwareFilter requestCacheAwareFilter() {
		return new RequestCacheAwareFilter();
	}

	public static SessionManagementFilter sessionManagementFilter() {
		return new SessionManagementFilter(
				new HttpSessionSecurityContextRepository());
	}

	public static LogoutFilter restLogoutFilter() {
		LogoutFilter restLogoutFilter = new LogoutFilter(
				new LogoutSuccessHandlerReturningOkHttpStatusCode(),
				new SecurityContextLogoutHandler());
		restLogoutFilter.setLogoutRequestMatcher(new AntPathRequestMatcher(
				"/services/rest/logout"));

		return restLogoutFilter;
	}

	public static LogoutFilter logoutFilter() {
		LogoutFilter logoutFilter = new LogoutFilter(
				"/security/login?logout=1", new SecurityContextLogoutHandler());
		logoutFilter.setFilterProcessesUrl("/j_spring_security_logout");

		return logoutFilter;
	}

	public static JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter(
			AuthenticationManager authenticationManager) {
		JsonUsernamePasswordAuthenticationFilter authenticationFilter = new JsonUsernamePasswordAuthenticationFilter(
				new AntPathRequestMatcher("/services/rest/login"));
		authenticationFilter.setAuthenticationManager(authenticationManager);
		authenticationFilter
				.setAuthenticationSuccessHandler(new AuthenticationSuccessHandlerSendingOkHttpStatusCode());
		authenticationFilter
				.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());

		return authenticationFilter;
	}

	public static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(
			AuthenticationManager authenticationManager) {
		org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter();
		usernamePasswordAuthenticationFilter
				.setAuthenticationManager(authenticationManager);
		usernamePasswordAuthenticationFilter
				.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(
						"/j_spring_security_login"));
		SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		savedRequestAwareAuthenticationSuccessHandler
				.setDefaultTargetUrl(LoginControllerDefaultTargetUrl.defaultTargetUrl);
		usernamePasswordAuthenticationFilter
				.setAuthenticationSuccessHandler(savedRequestAwareAuthenticationSuccessHandler);
		SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler();
		simpleUrlAuthenticationFailureHandler
				.setDefaultFailureUrl("/security/login?authentication_failed=1");
		usernamePasswordAuthenticationFilter
				.setAuthenticationFailureHandler(simpleUrlAuthenticationFailureHandler);

		return usernamePasswordAuthenticationFilter;
	}

	public static ExceptionTranslationFilter exceptionTranslationFilter() {
		ExceptionTranslationFilter exceptionTranslationFilter = new ExceptionTranslationFilter(
				new LoginUrlAuthenticationEntryPoint("/security/login"));
		AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
		accessDeniedHandler.setErrorPage("/security/login?access_denied=1");
		exceptionTranslationFilter.setAccessDeniedHandler(accessDeniedHandler);

		return exceptionTranslationFilter;
	}

	public static ExceptionTranslationFilter restExceptionTranslationFilter() {
		ExceptionTranslationFilter restExceptionTranslationFilter = new ExceptionTranslationFilter(
				new Http403ForbiddenEntryPoint());
		restExceptionTranslationFilter
				.setAccessDeniedHandler(new AccessDeniedHandlerImpl());

		return restExceptionTranslationFilter;
	}

	public static RequestContextFilter requestContextFilter() {
		return new RequestContextFilter();
	}

	public static RetainAnchorFilter retainAnchorFilter() {
		RetainAnchorFilter retainAnchorFilter = new RetainAnchorFilter();
		retainAnchorFilter
				.setStoreUrlPattern("http://[^/]+/[^/]+/security/login.*");
		retainAnchorFilter.setRestoreUrlPattern("http://[^/]+/[^/]+/.*");
		retainAnchorFilter.setCookieName("TARGETANCHOR");

		return retainAnchorFilter;
	}
	
	public static ToggleFilterWrapper csrfFilterWrapper(CsrfFilter csrfFilter,String disabledString){
		ToggleFilterWrapper csrfFilterWrapper = new ToggleFilterWrapper();
		csrfFilterWrapper.setDelegateFilter(csrfFilter);
		csrfFilterWrapper.setDisabledString(disabledString);
		
		return csrfFilterWrapper;
	}

	public static CsrfFilter csrfFilter(
			HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository,
			ApplicationAccessDeniedHandler accessDeniedHandler){
		CsrfFilter csrfFilter = new CsrfFilter(httpSessionCsrfTokenRepository);
		csrfFilter.setAccessDeniedHandler(accessDeniedHandler);
		
		return csrfFilter;
	}
}
