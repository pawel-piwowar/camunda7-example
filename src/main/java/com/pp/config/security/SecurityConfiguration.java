package com.pp.config.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ClaimsExtractor claimsExtractor;

    public SecurityConfiguration(ClaimsExtractor claimsExtractor) {
        this.claimsExtractor = claimsExtractor;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/Customers/**")
                .authenticated()
                .and()
                .addFilterBefore(new JwtTokenFilter(claimsExtractor), UsernamePasswordAuthenticationFilter.class);


    }




}

