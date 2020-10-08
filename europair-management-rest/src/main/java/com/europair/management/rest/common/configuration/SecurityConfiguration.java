package com.europair.management.rest.common.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(2)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            // -- h2
            "/h2/**",
            // -- other
            "/error**"
    };

    /* new autentication with AZURE AAD */
    @Profile("dev")
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
    }


}
