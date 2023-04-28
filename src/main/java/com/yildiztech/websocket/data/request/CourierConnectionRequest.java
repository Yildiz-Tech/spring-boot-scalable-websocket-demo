package com.yildiztech.websocket.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierConnectionRequest {

  private String courierCode;

  private Boolean connectionStatus;
}
