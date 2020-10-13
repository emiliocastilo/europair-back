package com.europair.management.rest.common.configuration;

import com.europair.management.rest.model.roles.entity.Role;
import com.europair.management.rest.model.users.entity.User;
import com.europair.management.rest.model.users.repository.IUserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
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

public class JWTAuthorizationFilterInternal extends BasicAuthenticationFilter {

  private static final String TOKEN_BEARER_PREFIX = "Bearer";
  private static final String HEADER_AUTHORIZACION_KEY = "Authorization";

  @Value("${europair.security.token.secret.key}")
  private String secretKey = "3ur0p41r";

  private IUserRepository userRepository;

  public JWTAuthorizationFilterInternal(AuthenticationManager authManager, IUserRepository userRepository) {
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
    chargeAuthoritiesIntoAuthenticationSecurityContextHolder();
    chain.doFilter(req, res);
  }

  private void chargeAuthoritiesIntoAuthenticationSecurityContextHolder() {
    String emailAzure = (String) ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaims().get("unique_name");
    Optional<User> azureUserInDDBB = userRepository.findByEmail(emailAzure);

    if (azureUserInDDBB.isPresent()) {
      // retrieve all the roles from the external user using the username and put into authorities
      Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
      List<Role> listRoleUser = new ArrayList<>();

      listRoleUser = azureUserInDDBB.get().getRoles();
      authorities = getAuthorities(listRoleUser);

      SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
              SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
              SecurityContextHolder.getContext().getAuthentication().getCredentials(),
              authorities
      ));
    }
  }

  private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (Role role : roles) {
      authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
    }

    return authorities;
  }

}
