package com.yildiztech.websocket.data;

import com.yildiztech.websocket.data.response.YtResponse;
import com.yildiztech.websocket.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.yildiztech.websocket.YtConstant.EMPTY_STRING;

@Component
public class RestResponseGenerator {

  public ResponseEntity<Object> success() {

    return success(EMPTY_STRING);
  }

  public <T> ResponseEntity<Object> success(T responseData) {

    return success(responseData, HttpStatus.OK);
  }

  public <T> ResponseEntity<Object> success(T responseData, HttpStatus httpStatus) {
    var routiResponse = YtResponse.<T>builder()
                                  .status(httpStatus.value())
                                  .data(responseData)
                                  .build();
    return new ResponseEntity<>(routiResponse, httpStatus);
  }

  public ResponseEntity<Object> error(HttpStatus httpStatus, String error, String errorMessage) {
    var routiResponse = YtResponse.builder()
                                  .status(httpStatus.value())
                                  .error(error)
                                  .message(errorMessage)
                                  .build();
    return new ResponseEntity<>(routiResponse, httpStatus);
  }

  public ResponseEntity<Object> error(HttpStatus httpStatus, String error, String errorMessage, String errorCode) {
    var routiResponse = YtResponse.builder()
                                  .status(httpStatus.value())
                                  .error(error)
                                  .errorCode(errorCode)
                                  .message(errorMessage)
                                  .build();
    return new ResponseEntity<>(routiResponse, httpStatus);
  }

  public <T> ResponseEntity<Object> error(HttpStatus httpStatus, String error, String errorMessage, T responseData) {
    var routiResponse = YtResponse.builder()
                                  .status(httpStatus.value())
                                  .error(error)
                                  .message(errorMessage)
                                  .data(responseData)
                                  .build();
    return new ResponseEntity<>(routiResponse, httpStatus);
  }

  public ResponseEntity<Object> badRequest(String errorMessage, ErrorCode errorCode) {
    var routiResponse = YtResponse.builder()
                                  .status(HttpStatus.BAD_REQUEST.value())
                                  .error(errorCode.getName())
                                  .errorCode(errorCode.getCode())
                                  .message(errorMessage)
                                  .build();
    return new ResponseEntity<>(routiResponse, HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<Object> badRequest(ErrorCode errorCode) {
    var routiResponse = YtResponse.builder()
                                  .status(HttpStatus.BAD_REQUEST.value())
                                  .error(errorCode.getName())
                                  .errorCode(errorCode.getCode())
                                  .message(errorCode.getName())
                                  .build();
    return new ResponseEntity<>(routiResponse, HttpStatus.BAD_REQUEST);
  }
}
