package com.yildiztech.websocket.service;

import com.yildiztech.websocket.data.response.TokenResponse;

public interface UsersService {

  TokenResponse login(String username, String password);
}
