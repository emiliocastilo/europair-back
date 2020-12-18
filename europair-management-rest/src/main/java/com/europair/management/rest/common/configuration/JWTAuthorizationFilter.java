package com.europair.management.rest.common.configuration;

import com.europair.management.rest.model.roles.entity.Role;
import com.europair.management.rest.model.users.entity.User;
import com.europair.management.rest.model.users.repository.IUserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

  private static final String TOKEN_BEARER_PREFIX = "Bearer";
  private static final String HEADER_AUTHORIZACION_KEY = "Authorization";

  @Value("${europair.security.token.secret.key}")
  private String secretKey = "3ur0p41r";

  private IUserRepository userRepository;

  public JWTAuthorizationFilter(AuthenticationManager authManager, IUserRepository userRepository) {
    super(authManager);
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
    throws IOException, ServletException {
    String header = req.getHeader(HEADER_AUTHORIZACION_KEY);
    if (header == null || !header.startsWith(TOKEN_BEARER_PREFIX)) {
      chain.doFilter(req, res);
      return;
    }
    UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

    // Check for expired token to throw 401 error
    final String expired = (String) req.getAttribute("expired");
    if (expired != null) {
      res.sendError(HttpServletResponse.SC_UNAUTHORIZED, expired);
    }

    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
    if (token != null) {
      // Process token and get user.
      String user = null;
      try {
        user = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
                .getBody()
                .getSubject();
      } catch (ExpiredJwtException e) {
        LOGGER.debug("Expired JWT token: " + e.getMessage());
        request.setAttribute("expired", e.getMessage());
      }

      if (user != null) {
        // retrieve all the roles from the external user using the username and put into authorities
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Role> listRoleUser = new ArrayList<>();
        Optional<User> optionalUser = userRepository.findByUsername(user);
        if(optionalUser.isPresent()){
          listRoleUser = optionalUser.get().getRoles();
          authorities = ((List<GrantedAuthority>) getAuthorities(listRoleUser));
        }

        return new UsernamePasswordAuthenticationToken(user, null, authorities);
      }
      return null;
    }
    return null;
  }

  private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (Role role: roles) {
      authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
    }

    return authorities;
  }

}
