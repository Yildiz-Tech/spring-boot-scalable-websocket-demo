package com.yildiztech.websocket.data.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {

  private String accessToken;

  private String tokenType;

  private int expiresInSec;
}
