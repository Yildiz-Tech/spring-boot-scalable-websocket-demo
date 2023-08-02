package com.yildiztech.websocket.exception.authentication;

import com.yildiztech.websocket.data.RestResponseGenerator;
import com.yildiztech.websocket.enums.ErrorCode;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class AuthenticationExceptionHandler {

  private final RestResponseGenerator restResponseGenerator;

  @ExceptionHandler({AuthenticationException.class,
                     UserCredentialException.class,
                     JwtTokenException.class,
                     SignatureException.class,
                     UsernameNotFoundException.class})
  public ResponseEntity<Object> handleAuthenticationException(final RuntimeException e, final WebRequest request) {
    log.warn("handleAuthenticationException {};", request, e);

    return restResponseGenerator.error(HttpStatus.UNAUTHORIZED,
                                       HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                                       ErrorCode.AUTHENTICATION_EXCEPTION.getName(),
                                       ErrorCode.AUTHENTICATION_EXCEPTION.getCode());
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Object> handleAccessDeniedException(final RuntimeException e, final WebRequest request) {
    log.error("handleAccessDeniedException {}; ", request, e);

    return restResponseGenerator.error(HttpStatus.FORBIDDEN,
                                       HttpStatus.FORBIDDEN.getReasonPhrase(),
                                       ErrorCode.ACCESS_DENIED_EXCEPTION.getName(),
                                       ErrorCode.ACCESS_DENIED_EXCEPTION.getCode());
  }
}
