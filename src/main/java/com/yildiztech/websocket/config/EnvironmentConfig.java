package com.yildiztech.websocket.config;

import jakarta.annotation.PostConstruct;
import java.util.Base64;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class EnvironmentConfig {

  @Value("${rabbitmq.url}")
  private String rabbitUrl;

  @Value("${rabbitmq.port}")
  private Integer rabbitPort;

  @Value("${rabbitmq.user}")
  private String rabbitUser;

  @Value("${rabbitmq.password}")
  private String rabbitPassword;

  @Value("${security.jwt.token.secret-key}")
  private String secretKey;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }
}
