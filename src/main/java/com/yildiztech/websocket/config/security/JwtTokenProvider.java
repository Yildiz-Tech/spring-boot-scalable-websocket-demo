package com.yildiztech.websocket.config.security;

import com.yildiztech.websocket.config.EnvironmentConfig;
import com.yildiztech.websocket.data.response.TokenResponse;
import com.yildiztech.websocket.exception.authentication.JwtTokenException;
import com.yildiztech.websocket.util.DateTimeUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import static com.yildiztech.websocket.YtConstant.AUTHORIZATION_JWT_TOKEN_START_INDEX;
import static com.yildiztech.websocket.YtConstant.AUTHORIZATION_QUERY_PARAMETER;
import static com.yildiztech.websocket.YtConstant.AUTHORIZATION_TYPE_BEARER;
import static com.yildiztech.websocket.YtConstant.HEADER_AUTHORIZATION;
import static com.yildiztech.websocket.YtConstant.JWT_TOKEN_CLAIMS_PARAM_AUTH;
import static com.yildiztech.websocket.YtConstant.JWT_TOKEN_CLAIMS_PARAM_NAME_SURNAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  private final UserDetailsService userDetailsService;

  private final EnvironmentConfig environmentConfig;

  public TokenResponse createToken(final String username) {

    var claims = Jwts.claims().setSubject(username);
    var now = LocalDateTime.now();
    var validityPeriod = 3200;

    String jwtToken = Jwts.builder()
                          .setClaims(claims)
                          .setIssuedAt(DateTimeUtil.convertLocalDateTimeToUtilDate(now))
                          .setExpiration(DateTimeUtil.convertLocalDateTimeToUtilDate(now.plusSeconds(validityPeriod)))
                          .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                          .compact();

    var tokenResponse = new TokenResponse();
    tokenResponse.setAccessToken(jwtToken);
    tokenResponse.setTokenType(AUTHORIZATION_TYPE_BEARER);
    tokenResponse.setExpiresInSec(validityPeriod);

    return tokenResponse;
  }

  public Authentication getAuthentication(String token) {
    String username = getUsername(token);
    var userDetails = userDetailsService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody().getSubject();
  }

  public String getNameSurname(String token) {
    return Jwts.parserBuilder()
               .setSigningKey(getSecretKey())
               .build()
               .parseClaimsJws(token)
               .getBody()
               .get(JWT_TOKEN_CLAIMS_PARAM_NAME_SURNAME, String.class);
  }

  public String getTokenValue(String bearerToken) {
    return bearerToken.substring(AUTHORIZATION_JWT_TOKEN_START_INDEX);
  }

  public String resolveToken(HttpServletRequest req) {
    var bearerToken = req.getHeader(HEADER_AUTHORIZATION);
    if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith(AUTHORIZATION_TYPE_BEARER)) {
      return bearerToken.substring(AUTHORIZATION_JWT_TOKEN_START_INDEX);
    }

    return req.getParameter(AUTHORIZATION_QUERY_PARAMETER);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      log.error("Exception occurred at validateToken token:{}", token, e);
      throw new JwtTokenException("Expired or Invalid JWT token", HttpStatus.UNAUTHORIZED);
    }
  }

  private SecretKey getSecretKey() {
    return Keys.hmacShaKeyFor(environmentConfig.getSecretKey().getBytes(StandardCharsets.UTF_8));
  }
}
