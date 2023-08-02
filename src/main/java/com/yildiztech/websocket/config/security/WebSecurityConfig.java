package com.yildiztech.websocket.config.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtTokenProvider jwtTokenProvider;

  private final CustomCorsFilter customCorsFilter;

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.cors().and().csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling()
        .authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ""))
        .and()
        .addFilterBefore(customCorsFilter, ChannelProcessingFilter.class)
        .authorizeHttpRequests()
        .requestMatchers("/web-socket/api/v1/token/provide").permitAll()
        .requestMatchers("/actuator/**").permitAll()
        .requestMatchers("/").permitAll()
        .anyRequest()
        .authenticated();

    // Apply JWT
    http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
                       .requestMatchers(
                           "/resources/**", "/static/**", "/css/**", "/js/**", "/images/**",
                           "/resources/static/**", "/css/**", "/js/**", "/img/**", "/fonts/**",
                           "/images/**", "/scss/**", "/vendor/**", "/favicon.ico", "/auth/**", "/favicon.png",
                           "/v2/api-docs", "/v3/api-docs", "/configuration/ui", "/configuration/security",
                           "/webjars/**", "/swagger-resources/**", "/actuator", "/swagger-ui/**",
                           "/actuator/**", "/swagger-ui/index.html", "/swagger-ui.html", "/swagger-ui/");
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http)
      throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
               .inMemoryAuthentication()
               .and()
               .build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
}
