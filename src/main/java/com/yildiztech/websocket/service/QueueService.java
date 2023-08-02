package com.yildiztech.websocket.service;

import com.yildiztech.websocket.data.request.CourierConnectionRequest;
import com.yildiztech.websocket.data.request.GpsRequest;

public interface QueueService {

  void putGpsMessage(GpsRequest gpsRequest);

  void putCourierConnectionMessage(CourierConnectionRequest gpsRequest);
}
