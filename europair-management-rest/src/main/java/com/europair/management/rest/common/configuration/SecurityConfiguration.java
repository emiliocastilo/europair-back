package com.europair.management.rest.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${cors.origins.allowed}")
    private String originsAllowed;

    @Autowired
    private UserDetailsService userDetailsService;

    @Profile("dev")
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
          .cors().and()
          .csrf().disable()
          .authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
          .anyRequest().authenticated().and()
          .addFilter(new JWTAuthenticationFilter(authenticationManager())) // have permissions
          .addFilter(new JWTAuthorizationFilter(authenticationManager())); // evaluate user
        httpSecurity.headers().frameOptions().disable();
    }

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v3/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            //h2
            "/h2/**",
            // other
            "/error**"
    };

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        configuration.setAllowedOrigins(Arrays.asList(StringUtils.tokenizeToStringArray(originsAllowed, ";")));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
