package com.yildiztech.websocket.api;

import com.yildiztech.websocket.data.request.CourierConnectionRequest;
import com.yildiztech.websocket.data.request.GpsRequest;
import com.yildiztech.websocket.service.QueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/web-socket/api/v1/queue")
public class QueueController {

  private final QueueService queueService;

  @PostMapping(value = "/gps",
               consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
  public void putGpsMessage(@RequestBody GpsRequest request) {
    queueService.putGpsMessage(request);
  }

  @PostMapping(value = "/courier-connection",
               consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
  public void putCourierConnectionMessage(@RequestBody CourierConnectionRequest request) {
    queueService.putCourierConnectionMessage(request);
  }
}
