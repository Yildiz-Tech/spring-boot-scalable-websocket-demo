package com.yildiztech.websocket.api;

import com.yildiztech.websocket.data.RestResponseGenerator;
import com.yildiztech.websocket.data.request.TokenRequest;
import com.yildiztech.websocket.data.response.TokenResponse;
import com.yildiztech.websocket.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/web-socket/api/v1/token")
public class TokenController {

  private final UsersService usersService;

  private final RestResponseGenerator restResponseGenerator;

  @PostMapping(value = "/provide", consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> provideToken(@Validated @RequestBody TokenRequest tokenRequestBody) {
    TokenResponse tokenResponse = usersService.login(tokenRequestBody.getUsername(), tokenRequestBody.getPassword());
    return restResponseGenerator.success(tokenResponse);
  }
}