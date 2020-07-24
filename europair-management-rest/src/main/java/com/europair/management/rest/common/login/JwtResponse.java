package com.europair.management.rest.common.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class JwtResponse implements Serializable {

  private final String jwtToken;

}
