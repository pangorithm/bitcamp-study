package com.bitcamp.myapp.handler;

import java.util.Scanner;

import com.bitcamp.util.Prompt;

public class ScheduleHandler {

  static Scanner scanner = new Scanner(System.in);

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

      System.out.println("일정 제목을 입력하세요");
      scheduleTitle[length] = scanner.nextLine();

      do {
        System.out.println("일정 시작 날짜와 시간을 입력하세요");
        System.out.println("ex)2023-06-05 16:30");
        startTime[length] = scanner.nextLine();
      } while (!checkDate(startTime[length]));

      do {
        System.out.println("일정 종료 날짜와 시간을 입력하세요");
        System.out.println("ex)2023-06-06 20:00");
        endTime[length] = scanner.nextLine();
      } while (!checkDate(endTime[length]));

      System.out.println("일정을 계속 입력 하시겠습니까?(y/N)");
      str = scanner.nextLine();

    } while (Prompt.promptContinue(str));

    scanner.close();
  }

  public static void printSchedules() {
    for (int i = 0; i < length; i++) {
      System.out.println(scheduleId[i]);
      System.out.println(scheduleTitle[i]);
      System.out.println(startTime[i]);
      System.out.println(endTime[i]);
    }
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
