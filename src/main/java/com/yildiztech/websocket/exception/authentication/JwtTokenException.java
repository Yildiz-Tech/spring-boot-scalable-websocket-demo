package com.yildiztech.websocket.exception.authentication;

import org.springframework.http.HttpStatus;

public class JwtTokenException extends RuntimeException {

  private static final long serialVersionUID = 3799294834178561781L;

  private final String message;

  private final HttpStatus httpStatus;

  public JwtTokenException(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
