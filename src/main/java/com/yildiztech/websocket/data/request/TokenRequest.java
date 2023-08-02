package com.yildiztech.websocket.data.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequest {

  @NotEmpty(message = "username should not be empty")
  private String username;

  @NotEmpty(message = "password should not be empty")
  private String password;
}
