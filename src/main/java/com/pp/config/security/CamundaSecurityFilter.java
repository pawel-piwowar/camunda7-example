package com.pp.config.security;

import org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class CamundaSecurityFilter {

    @Bean
    public FilterRegistrationBean processEngineAuthenticationFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("camunda-auth");
        registration.setFilter(getProcessEngineAuthenticationFilter());
        registration.addInitParameter("authentication-provider",
                "com.pp.config.security.CamundaAuthProvider");
        registration.addUrlPatterns("/engine-rest/*");
        return registration;
    }

    @Bean
    public Filter getProcessEngineAuthenticationFilter() {
        return new ProcessEngineAuthenticationFilter();
    }
}
