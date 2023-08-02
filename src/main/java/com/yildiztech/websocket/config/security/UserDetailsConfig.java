package com.yildiztech.websocket.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsConfig {

  @Bean
  public UserDetailsService users() {
    var izzet = User.builder()
                    .username("izzet.kilic@yilditech.co")
                    .password("{noop}izzet321!")
                    .roles("USER")
                    .build();
    var emre = User.builder()
                   .username("emre.kiziltepe@yilditech.co")
                   .password("{noop}emre123!")
                   .roles("USER", "ADMIN")
                   .build();
    return new InMemoryUserDetailsManager(izzet, emre);
  }
}
