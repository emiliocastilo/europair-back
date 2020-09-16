package com.europair.management.rest.common.configuration;

import com.europair.management.rest.common.login.JwtRequest;
import com.europair.management.rest.common.login.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

  private static final String TOKEN_BEARER_PREFIX = "Bearer";
  private static final String HEADER_AUTHORIZACION_KEY = "Authorization";
  private static final String ISSUER_INFO = "Europair";

  private final AuthenticationManager authenticationManager;

  // TODO: fix value annotation
  @Value("${europair.security.token.expiration.time:300000}") // 5 min.
  private final Long expirationTime = 1296000000L; // 15 días, es provisional.

  @Value("${europair.security.token.secret.key:3ur0p41r}")
  private final String secretKey = "3ur0p41r";

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
    throws AuthenticationException {
    try {

      System.out.println("Estoy en JWTAuthenticationFilter -> attemptAuthentication");
      LOGGER.debug("Estoy en JWTAuthenticationFilter -> attemptAuthentication!!!! Por fin!!!");


      JwtRequest jwtRequest = new ObjectMapper().readValue(request.getInputStream(), JwtRequest.class);

      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
              jwtRequest.getUsername(), jwtRequest.getPassword(), new ArrayList<>()));

      // load userName in httpsession
      request.getSession().setAttribute("userName", jwtRequest.getUsername());

      return authentication;
    } catch (IOException e) {
      throw new RuntimeException("Error de login", e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
    Authentication auth) throws IOException {

    System.out.println("Estoy en JWTAuthenticationFilter -> successfulAuthentication");

    String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(ISSUER_INFO)
      .setSubject(((User)auth.getPrincipal()).getUsername())
      .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
      .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    response.addHeader(HEADER_AUTHORIZACION_KEY, TOKEN_BEARER_PREFIX + " " + token);
    response.getOutputStream().print(new ObjectMapper().writeValueAsString(new JwtResponse(token)));
  }
}
