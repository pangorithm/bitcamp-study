/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.bitcamp.myapp;


import java.util.Scanner;

public class App {
  static Scanner scanner = new Scanner(System.in);

  static final int MAX_SIZE = 100;

  static String[] scheduleTitle = new String[MAX_SIZE];
  static String[] startTime = new String[MAX_SIZE];
  static String[] endTime = new String[MAX_SIZE];
  static int[] scheduleId = new int[MAX_SIZE];

  static int length = 0;
  static boolean promptContinue = true;

  public static void main(String[] args) {
    System.out.println("스캐줄 관리 애플리케이션");

    inputSchedule();

    printSchedules();

    scanner.close();
  }

  static void inputSchedule() {
    for (; promptContinue; length++) {
      scheduleId[length] = length + 1;

      System.out.println("일정 제목을 입력하세요");
      scheduleTitle[length] = scanner.nextLine();

      System.out.println("일정 시작 날짜와 시간을 입력하세요");
      System.out.println("ex)2023-06-05 16:30");
      startTime[length] = scanner.nextLine();
      // String str = scanner.nextLine();
      // double startTimeInt = Double.parseDouble(str.replaceAll("-", "").replaceAll("
      // ", "").replaceAll(":", ""));

      System.out.println("일정 종료 날짜와 시간을 입력하세요");
      System.out.println("ex)2023-06-06 20:00");
      endTime[length] = scanner.nextLine();
      // str = scanner.nextLine();
      // double endTimeInt = Double.parseDouble(str.replaceAll("-", "").replaceAll("
      // ", "").replaceAll(":", ""));}

      System.out.println("일정을 계속 입력 하시겠습니까?(y/N)");
      String str = scanner.nextLine();
      promptContinue = str.equalsIgnoreCase("y");

    }
  }

  static void printSchedules() {
    for (int i = 0; i < length; i++) {
      System.out.println(scheduleId[i]);
      System.out.println(scheduleTitle[i]);
      System.out.println(startTime[i]);
      System.out.println(endTime[i]);
    }
  }
}
