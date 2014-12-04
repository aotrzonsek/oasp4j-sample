package io.oasp.gastronomy.restaurant.security;

import io.oasp.module.security.common.impl.rest.JsonUsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.filter.RequestContextFilter;

import javax.inject.Inject;
import javax.inject.Named;

@Configuration
@Import({MethodAccessDecisionManager.class, SecurityFilterConfigurator.class})
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfigurator extends WebSecurityConfigurerAdapter {

    @Inject
    private RequestContextFilter requestContextFilter;
    @Inject
    private SecurityContextPersistenceFilter securityContextPersistenceFilter;
    @Inject
    @Named("RestLogoutFilter")
    private LogoutFilter restLogoutFilter;
    @Inject
    private JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter;
    @Inject
    private RequestCacheAwareFilter requestCacheAwareFilter;
    @Inject
    private SecurityContextHolderAwareRequestFilter securityContextHolderAwareRequestFilter;
    @Inject
    private AnonymousAuthenticationFilter anonymousAuthenticationFilter;
    @Inject
    private SessionManagementFilter sessionManagementFilter;
    @Inject
    @Named("RestExceptionTranslationFilter")
    private ExceptionTranslationFilter restExceptionTranslationFilter;
    @Inject
    private FilterSecurityInterceptor filterSecurityInterceptor;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/jsclient/**");

        http.requestMatchers().antMatchers("/services/rest/login1")
                .and()
                .addFilterAfter(requestContextFilter, WebAsyncManagerIntegrationFilter.class)
                .addFilterAfter(securityContextPersistenceFilter, RequestContextFilter.class)
                .addFilterAfter(restLogoutFilter, SecurityContextPersistenceFilter.class)
                .addFilterAfter(jsonUsernamePasswordAuthenticationFilter, LogoutFilter.class)
                .addFilterAfter(requestCacheAwareFilter, JsonUsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(securityContextHolderAwareRequestFilter, RequestCacheAwareFilter.class)
                .addFilterAfter(anonymousAuthenticationFilter, AnonymousAuthenticationFilter.class)
                .addFilterAfter(sessionManagementFilter, SessionManagementFilter.class)
                .addFilterAfter(restExceptionTranslationFilter, SessionManagementFilter.class)
                .addFilterAfter(filterSecurityInterceptor, ExceptionTranslationFilter.class);
    }
}
