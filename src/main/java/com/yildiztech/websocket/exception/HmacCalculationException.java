package com.yildiztech.websocket.exception;

public class HmacCalculationException extends RuntimeException {

  public HmacCalculationException(String msg) {
    super(msg);
  }

  public HmacCalculationException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
