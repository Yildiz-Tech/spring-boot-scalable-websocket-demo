package com.yildiztech.websocket.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YtResponse<T> {

  private int status;

  private String error;

  private String errorCode;

  private String message = "No message available";

  private T data;
}
