package com.yildiztech.websocket.exception.authentication;

public class UserCredentialException extends RuntimeException {

  private static final long serialVersionUID = 8267453900103598627L;

  public UserCredentialException(String msg) {
    super(msg);
  }

  public UserCredentialException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
