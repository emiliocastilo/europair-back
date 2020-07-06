package com.europair.management.rest.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> globalExcpetionHandler(Exception ex, WebRequest request) {
    ApiErrorDTO errorDTO =
      ApiErrorDTO.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .message(ex.getMessage())
        .build();
    return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
    ApiErrorDTO errorDTO =
        ApiErrorDTO.builder()
          .timestamp(LocalDateTime.now())
          .status(HttpStatus.NOT_FOUND)
          .message(ex.getMessage())
          .build();
    return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
  }

}
