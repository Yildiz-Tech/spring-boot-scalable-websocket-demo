package com.yildiztech.websocket.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
public class ObjectMapperUtil {

  private ObjectMapperUtil() {
    throw new IllegalStateException("Utility class");
  }

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static String toJson(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (Exception e) {
      log.error("Exception at ObjectMapperUtil.toJson: {}", object);
      return null;
    }
  }

  public static <T> T messageToObject(Class<T> clazz, String message) {
    try {
      objectMapper.registerSubtypes(clazz);
      return objectMapper.readerFor(clazz).readValue(message);
    } catch (Exception e) {
      log.error("Exception at ObjectMapperUtil.messageToObject: {}", message, e);
      return null;
    }
  }
}
