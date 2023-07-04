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
      String scheduleTitle = prompt.inputString("스케줄 제목을 입력하세요\n> ");

      System.out.println("스케줄 시작 날짜와 시간을 입력하세요");
      System.out.println("ex)2023-06-05 08:30");
      long startTime = inputTime(prompt);

      System.out.println("스케줄 종료 날짜와 시간을 입력하세요");
      System.out.println("ex)2023-06-06 20:00");
      long endTime = inputTime(prompt);
      System.out.println("--------------------------------------------------");

      int count = searchSchedules(startTime, endTime);
      if (count != 0) {
        System.out.println("입력한 스케줄과 중복되는 스캐줄이 " + count + "개 있습니다.");
        if (!prompt.promptContinue(prompt.inputString("해당 스케줄을 저장 하시겠습니까? (y/N)"))) {
          return null;
        }
      }

      sch.setScheduleTitle(scheduleTitle);
      sch.setStartTime(startTime);
      sch.setEndTime(endTime);
      printScheduleInfo(sch);
      System.out.println("스케줄이 추가되었습니다.");

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
      if (time.length() != 12) {
        System.out.println("올바르지 않은 입력입니다. 다시 입력해주시기 바랍니다.");
      }
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

  public int searchSchedules(long searchRangeStart, long searchRangeEnd) {
    int count = 0;
    for (Object obj : this.list.toArray()) {
      Schedule sch = (Schedule) obj;
      if (sch.getEndTime() > searchRangeStart && sch.getStartTime() < searchRangeEnd) {
        printScheduleInfo(sch);
        count++;
      }
    }
    return count;
  }

  protected static long localDateTimeToLong(LocalDateTime localDateTime) {
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

  protected static LocalDateTime longToLocalDateTime(long milliseconds) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
  }
}
