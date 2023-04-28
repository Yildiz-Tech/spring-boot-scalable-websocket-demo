package com.yildiztech.websocket.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GpsRequest {

  private String plateNumber;

  private double latitude;

  private double longitude;
}
