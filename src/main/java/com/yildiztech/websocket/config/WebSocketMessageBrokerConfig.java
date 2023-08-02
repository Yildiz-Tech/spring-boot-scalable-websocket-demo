package com.yildiztech.websocket.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 200)
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig
    implements WebSocketMessageBrokerConfigurer {

  private final EnvironmentConfig environmentConfig;

  private static final String[] APP_PREFIXES = new String[] {"/app", "/exchange"};

  public static final String[] BROKER_PREFIXES = new String[] {"/queue", "/topic", "/exchange"};

  public static final int MAX_WORKERS_COUNT = Math.max(2, Runtime.getRuntime().availableProcessors());

  public static final int TASK_QUEUE_SIZE = 10_000;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.setPreservePublishOrder(true)
            .setApplicationDestinationPrefixes(APP_PREFIXES)
            .enableStompBrokerRelay(BROKER_PREFIXES)
            .setRelayHost(environmentConfig.getRabbitUrl())
            .setRelayPort(environmentConfig.getRabbitPort())
            .setClientLogin(environmentConfig.getRabbitUser())
            .setClientPasscode(environmentConfig.getRabbitPassword())
            .setSystemLogin(environmentConfig.getRabbitUser())
            .setSystemPasscode(environmentConfig.getRabbitPassword())
            .setUserDestinationBroadcast("/topic/unresolved-user")
            .setUserRegistryBroadcast("/topic/user-registry");

    registry.configureBrokerChannel()
            .taskExecutor().corePoolSize(1).maxPoolSize(MAX_WORKERS_COUNT).queueCapacity(TASK_QUEUE_SIZE);
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/live-feed-gps").setAllowedOriginPatterns("*localhost*");
    registry.addEndpoint("/courier-connection-status").setAllowedOriginPatterns("*localhost*");
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.taskExecutor().corePoolSize(1).maxPoolSize(MAX_WORKERS_COUNT).queueCapacity(TASK_QUEUE_SIZE);
  }

  @Override
  public void configureClientOutboundChannel(ChannelRegistration registration) {
    registration.taskExecutor().corePoolSize(1).maxPoolSize(MAX_WORKERS_COUNT).queueCapacity(TASK_QUEUE_SIZE);
  }
}
