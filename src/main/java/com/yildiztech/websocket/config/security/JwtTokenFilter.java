package com.yildiztech.websocket.config.security;

import com.yildiztech.websocket.exception.authentication.JwtTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
public class JwtTokenFilter extends GenericFilterBean {

  private JwtTokenProvider jwtTokenProvider;

  public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
      throws IOException, ServletException {

    try {
      String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);

      if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
        Authentication auth = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    } catch (JwtTokenException e) {
      log.error("Exception occurred at doFilter RemoteAddr:{} RemoteHost:{} RemotePort:{} Ex:",
                req.getRemoteAddr(), req.getRemoteHost(), req.getRemotePort(), e);
      //sendErrorMessage((HttpServletResponse) res, e);
      return;
    }

    filterChain.doFilter(req, res);
  }

  //private void sendErrorMessage(HttpServletResponse res, JwtTokenException e) throws IOException {
  //  HttpServletResponse response = res;
  //  final RoutiResponse webSocketResponse = RoutiResponse.builder().status(HttpStatus.UNAUTHORIZED.value())
  //                                                       .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
  //                                                       .message(e.getMessage()).build();
  //  response.setStatus(HttpStatus.UNAUTHORIZED.value());
  //  response.setContentType(MediaType.APPLICATION_JSON_VALUE);
  //  response.getWriter().write(GsonUtil.toJson(webSocketResponse));
  //  response.getWriter().flush();
  //}
}
