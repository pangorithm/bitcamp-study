package com.bitcamp.myapp.handler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.Prompt;

public abstract class AbstractScheduleListener implements ActionListener {
  protected List<Schedule> list;

  public AbstractScheduleListener(List<Schedule> list) {
    this.list = list;
  }


  protected Schedule inputScheduleInfo(Schedule sch, Prompt prompt) {
    try {
      sch.setScheduleTitle(prompt.inputString("스케줄 제목을 입력하세요\n> "));

      System.out.println("스케줄 시작 날짜와 시간을 입력하세요");
      System.out.println("ex)2023-06-05 08:30");
      sch.setStartTime(inputTime(prompt));

      System.out.println("스케줄 종료 날짜와 시간을 입력하세요");
      System.out.println("ex)2023-06-06 20:00");
      sch.setEndTime(inputTime(prompt));
    } catch (Exception e) {
      System.out.println("날짜 입력 중 오류 발생");
      Schedule.scheduleId--;
      return null;
    }
    return sch;
  }

  protected void printScheduleInfo(Schedule sch) {
    System.out.print("번호: ");
    System.out.println(sch.getNo());
    System.out.print("제목: ");
    System.out.println(sch.getScheduleTitle());
    System.out.print("시작: ");
    System.out.println(longToLocalDateTime(sch.getStartTime()).toString());
    System.out.print("종료: ");
    System.out.println(longToLocalDateTime(sch.getEndTime()).toString());
    System.out.println("");
  }

  protected long inputTime(Prompt prompt) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    String time;
    do {
      time = prompt.inputString("> ").replaceAll("[^0-9]", "");
    } while (time.length() != 12);
    return localDateTimeToLong(LocalDateTime.parse(time, formatter));
  }

  protected int findBy(int no) {
    Object[] list = this.list.toArray();
    for (int i = 0; i < this.list.size(); i++) {
      Schedule b = (Schedule) list[i];
      if (b.getNo() == no) {
        return i;
      }
    }
    return -1;
  }

  protected static long localDateTimeToLong(LocalDateTime localDateTime) {
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

  protected static LocalDateTime longToLocalDateTime(long milliseconds) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
  }
}
