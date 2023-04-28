package com.yildiztech.websocket.service.impl;

import com.yildiztech.websocket.config.security.JwtTokenProvider;
import com.yildiztech.websocket.data.response.TokenResponse;
import com.yildiztech.websocket.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

  private final JwtTokenProvider jwtTokenProvider;

  private final AuthenticationManager authenticationManager;

  public TokenResponse login(String username, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    return jwtTokenProvider.createToken(username);
  }
}
