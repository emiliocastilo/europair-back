package com.europair.management.rest.common.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Configuration
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests()
                .antMatchers("/h2/**").permitAll();
        if(Arrays.asList(StringUtils.tokenizeToStringArray(activeProfile,",")).contains("dev")){
            // TODO
            log.info("Enabling H2 AntMatchers for develop profile");
        }
        httpSecurity.headers().frameOptions().disable();
    }
}
