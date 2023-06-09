package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.Prompt;

public class ScheduleHandler {

  static final int MAX_SIZE = 100;

  static Schedule[] schedules = new Schedule[MAX_SIZE];

  static int length = 0;
  static String str;

  public static void inputSchedule() {
    do {
      inputScheduleInfo(length);
      length++;
      str = Prompt.inputString("일정을 계속 추가 하시겠습니까?(y/N) \n> ");

    } while (Prompt.promptContinue(str));
  }

  public static void printSchedules() {
    for (int i = 0; i < length; i++) {
      printScheduleInfo(i);
    }
  }

  public static void searchSchedules() {
    System.out.println("어떤 조건으로 검색하시겠습니까?");
    System.out.println("1. 스케줄 제목");
    System.out.println("2. 날짜 범위");
    String optNo = Prompt.inputString("> ");

    if (optNo.equals("1")) {
      String searchTitle = Prompt.inputString("제목 : ");
      for (int i = 0; i < length; i++) {
        Schedule sch = schedules[i];
        if (sch.getScheduleTitle().contains(searchTitle)) {
          printScheduleInfo(i);
        }
      }
    } else if (optNo.equals("2")) {
      long searchRangeStart = parseDateLong(Prompt.inputString("검색 시작 시간 ex)2023-06-05 16:30\n> "));
      long searchRangeEnd = parseDateLong(Prompt.inputString("검색 종료 시간 ex)2023-06-06 20:00\n> "));
      for (int i = 0; i < length; i++) {
        Schedule sch = schedules[i];
        if (sch.getEndTime() > searchRangeStart && sch.getStartTime() < searchRangeEnd) {
          printScheduleInfo(i);
        }
      }
    } else {
      System.out.println("올바르지 않은 형식입니다.");
    }
  }

  public static void updateSchedule() {
    int inputNo = Prompt.inputInt("번호? ");
    int i = indexOf((inputNo));
    if (i > -1) {
      System.out.println("수정 전--------------");
      printScheduleInfo(i);
      System.out.println("---------------------");
      inputScheduleInfo(i);
    } else {
      System.out.println("일치하는 번호가 존재하지 않습니다.");
    }
  }

  public static void deleteSchdule() {
    int memberNo = Prompt.inputInt("번호? ");

    int deletedIndex = indexOf(memberNo);
    if (deletedIndex == -1) {
      System.out.println("해당 번호의 스케줄이 없습니다!");
      return;
    }

    for (int i = deletedIndex; i < length - 1; i++) {
      schedules[i] = schedules[i + 1];
    }

    schedules[length - 1] = null;

    length--;
  }

  private static void inputScheduleInfo(int index) {
    Schedule sch = schedules[index] == null ? new Schedule() : schedules[index];
    sch.setScheduleTitle(Prompt.inputString("스케줄 제목을 입력하세요\n> "));

    do {
      System.out.println("스케줄 시작 날짜와 시간을 입력하세요");
      System.out.println("ex)2023-06-05 08:30");
      sch.setStartTime(parseDateLong(Prompt.inputString("> ")));
    } while (!checkDate(sch.getStartTime()));

    do {
      System.out.println("스케줄 종료 날짜와 시간을 입력하세요");
      System.out.println("ex)2023-06-06 20:00");
      sch.setEndTime(parseDateLong(Prompt.inputString("> ")));
    } while (!checkDate(sch.getEndTime()));

    schedules[index] = sch;
  }

  private static void printScheduleInfo(int index) {
    Schedule sch = schedules[index];

    System.out.print("번호: ");
    System.out.println(sch.getNo());
    System.out.print("제목: ");
    System.out.println(sch.getScheduleTitle());
    System.out.print("시작: ");
    System.out.println(dateToString(sch.getStartTime()));
    System.out.print("종료: ");
    System.out.println(dateToString(sch.getEndTime()));
    System.out.println("");
  }

  static long parseDateLong(String date) {
    String str = date.replaceAll("[^0-9]", "");
    long dateLong = Long.parseLong(str);
    return dateLong;
  }

  static String dateToString(long date) {
    String dateString = "";

    int min = (int) (date % 100);
    date /= 100;
    int hour = (int) (date % 100);
    date /= 100;
    int day = (int) (date % 100);
    date /= 100;
    int month = (int) (date % 100);
    date /= 100;
    int year = (int) (date % 10000);

    dateString = year + "년" + month + "월" + day + "일 " + hour + ":" + min;

    return dateString;
  }

  static boolean checkDate(long date) {

    int min = (int) (date % 100);
    date /= 100;
    int hour = (int) (date % 100);
    date /= 100;
    int day = (int) (date % 100);
    date /= 100;
    int month = (int) (date % 100);
    date /= 100;
    // int year = (int) (date % 10000);

    if (month < 1 || month > 12) {
      System.out.println("올바르지 않은 날짜 형식입니다.");
      return false;
    } else if (day < 1 || day > 31) {
      System.out.println("올바르지 않은 날짜 형식입니다.");
      return false;
    } else if (hour < 0 || hour > 24) {
      System.out.println("올바르지 않은 날짜 형식입니다.");
      return false;
    } else if (min < 0 || min > 59) {
      System.out.println("올바르지 않은 날짜 형식입니다.");
      return false;
    } else {
      return true;
    }
  }

  private static int indexOf(int inputNo) {
    for (int i = 0; i < length; i++) {
      if (schedules[i].getNo() == inputNo) {
        return i;
      }
    }
    return -1;
  }
}
