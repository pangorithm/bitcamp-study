package com.bitcamp.myapp.handler;

import java.security.Principal;

import com.bitcamp.util.Prompt;

public class ScheduleHandler {

  static final int MAX_SIZE = 100;

  static String[] scheduleTitle = new String[MAX_SIZE];
  static String[] startTime = new String[MAX_SIZE];
  static String[] endTime = new String[MAX_SIZE];
  static int[] scheduleId = new int[MAX_SIZE];

  static int length = 0;
  static String str;

  public static void inputSchedule() {
    do {
      scheduleId[length] = ++length;

      scheduleTitle[length] = Prompt.inputString("일정 제목을 입력하세요\n");

      do {
        System.out.println("일정 시작 날짜와 시간을 입력하세요");
        System.out.println("ex)2023-06-05 16:30");
        startTime[length] = Prompt.inputString("> ");
      } while (!checkDate(startTime[length]));

      do {
        System.out.println("일정 종료 날짜와 시간을 입력하세요");
        System.out.println("ex)2023-06-06 20:00");
        endTime[length] = Prompt.inputString("> ");
      } while (!checkDate(endTime[length]));

      str = Prompt.inputString("일정을 계속 입력 하시겠습니까?(y/N)\n >");

    } while (Prompt.promptContinue(str));

    Prompt.close();
  }

  public static void printSchedules() {
    for (int i = 0; i < length; i++) {
      printScheduleInfo(i);
    }
  }

  public static void searchSchedules() {
    System.out.println("어떤 조건으로 검색하시겠습니까?");
    System.out.println("1. 스캐줄 제목");
    System.out.println("2. 날짜 범위");
    String optNo = Prompt.inputString("> ");

    if (optNo.equals("1")) {
      String searchTitle = Prompt.inputString("제목 : ");
      for (int i = 0; i < length; i++) {
        if (scheduleTitle[i].contains(searchTitle)) {
          printScheduleInfo(i);
          System.out.println("");
        }
      }

    } else if (optNo.equals("2")) {
      String searchRangeStart = Prompt.inputString("검색 시작 시간 ex)2023-06-05 16:30");
      for (int i = 0; i < length; i++) {
        if (scheduleTitle[i].contains()) {
          printScheduleInfo(i);
          System.out.println("");
        }
      }
    } else {
      System.out.println("올바르지 않은 번호입니다.");
    }
  }

  static void printScheduleInfo(int index) {
    System.out.println(scheduleId[index]);
    System.out.println(scheduleTitle[index]);
    System.out.println(startTime[index]);
    System.out.println(endTime[index]);
  }

  static int parseDateInt(String date) {
    return;
  }

  static boolean checkDate(String date) {
    String str = date.replaceAll("[^0-9]", "");
    if (str.length() < 12) {
      System.out.println("올바르지 않은 날짜 형식입니다.");
      return false;
    }

    int year = Integer.parseInt(str.substring(0, 4));
    int month = Integer.parseInt(str.substring(4, 6));
    int day = Integer.parseInt(str.substring(6, 8));
    int hour = Integer.parseInt(str.substring(8, 10));
    int min = Integer.parseInt(str.substring(10));

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

}
