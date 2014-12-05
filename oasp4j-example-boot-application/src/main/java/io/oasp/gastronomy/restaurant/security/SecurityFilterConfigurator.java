package io.oasp.gastronomy.restaurant.security;

import io.oasp.module.security.common.impl.rest.AuthenticationSuccessHandlerSendingOkHttpStatusCode;
import io.oasp.module.security.common.impl.rest.JsonUsernamePasswordAuthenticationFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.RequestContextFilter;

import javax.inject.Inject;

@Configuration
public class SecurityFilterConfigurator {

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return new BeanFactoryPostProcessor() {
            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                System.out.println("");
               // BeanDefinitionRegistry.class.cast(beanFactory).removeBeanDefinition("JsonUsernamePasswordAuthenticationFilter");
            }
        };
    }

      public static JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        JsonUsernamePasswordAuthenticationFilter authenticationFilter =
                new JsonUsernamePasswordAuthenticationFilter(new AntPathRequestMatcher("/services/rest/login"));
        authenticationFilter.setAuthenticationManager(authenticationManager);
        authenticationFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandlerSendingOkHttpStatusCode());
        authenticationFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());

        return authenticationFilter;
    }

//    public static FilterRegistrationBean filterRegistrationBean(AuthenticationManager authenticationManager) {
//        JsonUsernamePasswordAuthenticationFilter authenticationFilter =
//                new JsonUsernamePasswordAuthenticationFilter(new AntPathRequestMatcher("/services/rest/login"));
//        authenticationFilter.setAuthenticationManager(authenticationManager);
//        authenticationFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandlerSendingOkHttpStatusCode());
//        authenticationFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());
//
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean(authenticationFilter);
//        registrationBean.addUrlPatterns("/services/rest/login");
//        return registrationBean;
//
//    }
}
