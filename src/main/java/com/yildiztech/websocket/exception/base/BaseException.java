package com.yildiztech.websocket.exception.base;

import java.io.Serializable;

public class BaseException extends RuntimeException implements Serializable {

  private static final long serialVersionUID = 5345122104392277009L;

  public BaseException() {
    super();
  }

  public BaseException(String message) {
    super(message);
  }

  public BaseException(String message, Throwable cause) {
    super(message, cause);
  }

  public BaseException(Throwable cause) {
    super(cause);
  }
}
