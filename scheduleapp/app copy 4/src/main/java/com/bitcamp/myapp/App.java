/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.bitcamp.myapp;


import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    System.out.println("스캐줄 관리 애플리케이션");

    Scanner scanner = new Scanner(System.in);

    System.out.println("일정 제목을 입력하세요");
    String scheduleTitle = scanner.nextLine();

    System.out.println("일정 시작 날짜와 시간을 입력하세요");
    System.out.println("ex)2023-06-05 16:30");
    String startTime = scanner.nextLine();
    // String str = scanner.nextLine();
    // double startTimeInt = Double.parseDouble(str.replaceAll("-", "").replaceAll("
    // ", "").replaceAll(":", ""));

    System.out.println("일정 종료 날짜와 시간을 입력하세요");
    System.out.println("ex)2023-06-06 20:00");
    String endTime = scanner.nextLine();
    // str = scanner.nextLine();
    // double endTimeInt = Double.parseDouble(str.replaceAll("-", "").replaceAll("
    // ", "").replaceAll(":", ""));

    System.out.println(scheduleTitle);
    System.out.println(startTime);
    System.out.println(endTime);

    scanner.close();
  }
}
