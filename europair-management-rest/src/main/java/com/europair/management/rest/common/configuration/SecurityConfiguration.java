package com.europair.management.rest.common.configuration;

import com.europair.management.rest.model.users.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(3)
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

    @Autowired
    private IUserRepository userRepository;

    /* new autentication with AZURE AAD */
    @Profile("dev")
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated().and().cors()
                .and()
                .addFilter(new JWTAuthorizationFilterInternal(authenticationManager(), userRepository))
                .oauth2ResourceServer()
                .jwt();

    }

}
