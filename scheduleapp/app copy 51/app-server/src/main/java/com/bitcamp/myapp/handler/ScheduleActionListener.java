package com.bitcamp.myapp.handler;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.Prompt;

public interface ScheduleActionListener extends ActionListener {

  static Schedule inputScheduleInfo(List<Schedule> list, Schedule sch, Prompt prompt)
      throws IOException {
    try {
      String scheduleTitle = prompt.inputString("스케줄 제목을 입력하세요\n> ");

      long startTime = inputTime("스케줄 시작 날짜와 시간을 입력하세요\n ex)2023-06-05 08:30\n", prompt);

      long endTime = inputTime("스케줄 종료 날짜와 시간을 입력하세요\n ex)2023-06-06 20:00\n", prompt);
      prompt.println("--------------------------------------------------");

      if (startTime > endTime) {
        throw new Exception("스케줄 시작이 종료보다 늦습니다");
      }
      LinkedList<Schedule> resultList = searchSchedules(list, startTime, endTime);
      for (Schedule printSch : resultList.toArray(new Schedule[0])) {
        printScheduleInfo(printSch, prompt);
      }
      prompt.writeBuf();

      if (resultList.size() != 0) {
        if (!prompt.promptContinue(prompt.inputString(
            "입력한 스케줄과 중복되는 스캐줄이 " + resultList.size() + "개 있습니다. \n해당 스케줄을 저장 하시겠습니까? (y/N)"))) {
          return null;
        }
      }

      sch.setScheduleTitle(scheduleTitle);
      sch.setStartTime(startTime);
      sch.setEndTime(endTime);
      sch.setOwner((Member) prompt.getAttribute("loginUser"));
      printScheduleInfo(sch, prompt);
      prompt.println("스케줄이 추가되었습니다.");

    } catch (Exception e) {
      prompt.println(e.getMessage());
      Schedule.scheduleId--;
      return null;
    }
    return sch;
  }

  static void printScheduleInfo(Schedule sch, Prompt prompt) throws IOException {
    if (sch.getNo() != 0) {
      prompt.print("번호: ");
      prompt.println(Integer.toString(sch.getNo()));
    }
    prompt.print("제목: ");
    prompt.println(sch.getScheduleTitle());
    prompt.print("시작: ");
    prompt.println(longToLocalDateTime(sch.getStartTime()).toString());
    prompt.print("종료: ");
    prompt.println(longToLocalDateTime(sch.getEndTime()).toString());
    prompt.println("");
  }

  public static LinkedList<Schedule> searchSchedules(List<Schedule> list, long searchRangeStart,
      long searchRangeEnd) {
    LinkedList<Schedule> resultList = new LinkedList<>();
    for (Object obj : list.toArray()) {
      Schedule sch = (Schedule) obj;
      if (sch.getEndTime() > searchRangeStart && sch.getStartTime() < searchRangeEnd) {
        resultList.add(sch);
      }
    }
    return resultList;
  }

  static long inputTime(String str, Prompt prompt) throws IOException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    String time;
    do {
      time = prompt.inputString(str + "> ").replaceAll("[^0-9]", "");
      if (time.length() != 12) {
        prompt.println("올바르지 않은 입력입니다. 다시 입력해주시기 바랍니다.");
        prompt.end();
      }
    } while (time.length() != 12);
    return localDateTimeToLong(LocalDateTime.parse(time, formatter));
  }


  static long localDateTimeToLong(LocalDateTime localDateTime) {
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

  static LocalDateTime longToLocalDateTime(long milliseconds) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
  }
}
