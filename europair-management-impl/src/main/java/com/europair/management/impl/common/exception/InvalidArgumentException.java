package com.europair.management.impl.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidArgumentException extends RuntimeException {

  public InvalidArgumentException(String message) {
    super(message);
  }

}
