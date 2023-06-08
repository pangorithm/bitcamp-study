package com.bitcamp.myapp.handler;

import com.bitcamp.util.Prompt;

public class ScheduleHandler {

  static final int MAX_SIZE = 100;

  static int[] no = new int[MAX_SIZE];
  static String[] scheduleTitle = new String[MAX_SIZE];
  static Double[] startTime = new Double[MAX_SIZE];
  static Double[] endTime = new Double[MAX_SIZE];

  static int length = 0;
  static int scheduleId = 1;
  static String str;

  public static void inputSchedule() {
    do {
      inputScheduleInfo(length);

      no[length] = scheduleId++;
      length++;
      str = Prompt.inputString("일정을 계속 입력 하시겠습니까?(y/N) \n> ");

    } while (Prompt.promptContinue(str));
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
        }
      }
    } else if (optNo.equals("2")) {
      Double searchRangeStart = parseDateDouble(Prompt.inputString("검색 시작 시간 ex)2023-06-05 16:30\n> "));
      Double searchRangeEnd = parseDateDouble(Prompt.inputString("검색 종료 시간 ex)2023-06-06 20:00\n> "));
      for (int i = 0; i < length; i++) {
        if (endTime[i] > searchRangeStart && startTime[i] < searchRangeEnd) {
          printScheduleInfo(i);
        }
      }
    } else {
      System.out.println("올바르지 않은 번호입니다.");
    }
  }

  public static void updateSchedule() {
    int inputNo = Prompt.inputInt("번호? ");
    int i = indexOf((inputNo));
    if (i > -1) {
      inputScheduleInfo(i);
    } else {
      System.out.println("일치하는 번호가 존재하지 않습니다.");
    }
  }

  public static void deleteSchdule() {
    int memberNo = Prompt.inputInt("번호? ");

    int deletedIndex = indexOf(memberNo);
    if (deletedIndex == -1) {
      System.out.println("해당 번호의 회원이 없습니다!");
      return;
    }

    for (int i = deletedIndex; i < length - 1; i++) {
      no[i] = no[i + 1];
      scheduleTitle[i] = scheduleTitle[i + 1];
      startTime[i] = startTime[i + 1];
      endTime[i] = endTime[i + 1];
    }

    no[length - 1] = 0;
    scheduleTitle[length - 1] = null;
    startTime[length - 1] = null;
    endTime[length - 1] = null;

    length--;
  }

  private static void inputScheduleInfo(int index) {
    scheduleTitle[index] = Prompt.inputString("일정 제목을 입력하세요\n> ");

    do {
      System.out.println("일정 시작 날짜와 시간을 입력하세요");
      System.out.println("ex)2023-06-05 16:30");
      startTime[index] = parseDateDouble(Prompt.inputString("> "));
    } while (!checkDate(startTime[index]));

    do {
      System.out.println("일정 종료 날짜와 시간을 입력하세요");
      System.out.println("ex)2023-06-06 20:00");
      endTime[index] = parseDateDouble(Prompt.inputString("> "));
    } while (!checkDate(endTime[index]));
  }

  private static void printScheduleInfo(int index) {
    System.out.print("번호: ");
    System.out.println(no[index]);
    System.out.print("제목: ");
    System.out.println(scheduleTitle[index]);
    System.out.print("시작: ");
    System.out.println(dateToString(startTime[index]));
    System.out.print("종료: ");
    System.out.println(dateToString(endTime[index]));
    System.out.println("");
  }

  static double parseDateDouble(String date) {
    String str = date.replaceAll("[^0-9]", "");
    double dateDouble = Double.parseDouble(str);
    return dateDouble;
  }

  static String dateToString(double date) {
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

  static boolean checkDate(Double date) {

    int min = (int) (date % 100);
    date /= 100;
    int hour = (int) (date % 100);
    date /= 100;
    int day = (int) (date % 100);
    date /= 100;
    int month = (int) (date % 100);
    date /= 100;
    int year = (int) (date % 10000);

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
      if (no[i] == inputNo) {
        return i;
      }
    }
    return -1;
  }
}
