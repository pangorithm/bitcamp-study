/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.bitcamp.scheduleapp;

import com.bitcamp.scheduleapp.handler.ScheduleHandler;

public class App {

  public static void main(String[] args) {
    System.out.println("스캐줄 관리 애플리케이션");

    ScheduleHandler.inputSchedule();

    ScheduleHandler.printSchedules();
  }

}