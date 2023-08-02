package com.yildiztech.websocket.config.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class CustomCorsFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    Filter.super.init(filterConfig);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    final var httpServletResponse = (HttpServletResponse) response;
    httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
    httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, PATCH, OPTIONS, DELETE");
    httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
    httpServletResponse.setHeader("Access-Control-Allow-Headers",
                                  "X-Requested-With, Authorization, Origin, Content-Type, Version, Accept");
    httpServletResponse.setHeader("Access-Control-Expose-Headers",
                                  "X-Requested-With, Authorization, Origin, Content-Type, Location");
    httpServletResponse.setHeader("Content-Type", "text/html");
    final var httpServletRequest = (HttpServletRequest) request;
    if (!httpServletRequest.getMethod().equals("OPTIONS")) {
      chain.doFilter(request, response);
    }
  }

  @Override
  public void destroy() {
    Filter.super.destroy();
  }
}
