package com.yildiztech.websocket.service.impl;

import com.yildiztech.websocket.YtConstant;
import com.yildiztech.websocket.data.request.CourierConnectionRequest;
import com.yildiztech.websocket.data.request.GpsRequest;
import com.yildiztech.websocket.service.QueueService;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {

  private final SimpMessagingTemplate simpMessagingTemplate;

  private final SimpUserRegistry simpUserRegistry;

  @Override
  public void putGpsMessage(GpsRequest request) {
    var users = simpUserRegistry.getUsers();
    users.forEach(simpUser -> {
      sendMessageToUser(simpUser, YtConstant.LIVE_FEED_GPS_STREAM, request);
    });
  }

  @Override
  public void putCourierConnectionMessage(CourierConnectionRequest request) {
    var users = simpUserRegistry.getUsers();
    users.forEach(simpUser -> {
      sendMessageToUser(simpUser, YtConstant.COURIER_CONNECTION_STATUS_STREAM, request);
    });
  }

  private void sendMessageToUser(SimpUser simpUser, String destination, Object message) {
    simpUser.getSessions().stream()
            .flatMap(simpSession -> simpSession.getSubscriptions().stream())
            .filter(subscription -> subscription.getDestination().contains(destination))
            .forEach(subscription -> {
              var header = new HashMap<String, Object>();
              header.put("auto-delete", "true");
              header.put("simpSessionId", subscription.getSession().getId());
              header.put(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
              simpMessagingTemplate.convertAndSendToUser(simpUser.getName(), destination, message, header);
            });
  }
}
