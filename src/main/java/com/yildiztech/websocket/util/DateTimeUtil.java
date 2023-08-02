package com.yildiztech.websocket.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;

import static com.yildiztech.websocket.YtConstant.TIMEZONE_TURKEY;
import static com.yildiztech.websocket.YtConstant.TIME_FORMAT;

@Slf4j
public class DateTimeUtil {

  private static final DateTimeFormatter REPORT_FILE_NAME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS");

  private static final DateTimeFormatter ONLY_TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

  private DateTimeUtil() {
  }

  public static ZoneId getZoneIdOfTurkey() {
    //Turkey or Europe/Istanbul or Asia/Istanbul
    return ZoneId.of(TIMEZONE_TURKEY);
  }

  public static java.util.Date convertLocalDateTimeToUtilDate(LocalDateTime localDateTime) {
    try {
      return java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    } catch (Exception e) {
      log.error("Exception occurred at convertLocalDateTimeToUtilDate Ex:", e);
    }
    return new java.util.Date();
  }

  public static LocalDate getTurkeyZoneCurrentDate() {
    return LocalDate.now(getZoneIdOfTurkey());
  }
}
