/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.bitcamp.myapp;

import com.bitcamp.myapp.handler.BoardHandler;
import com.bitcamp.myapp.handler.ScheduleHandler;
import com.bitcamp.util.Prompt;

public class App {

  public static void main(String[] args) {

    Prompt prompt = new Prompt();
    ScheduleHandler schduleHandler = new ScheduleHandler(prompt, "스케줄");
    BoardHandler boardHandler = new BoardHandler(prompt, "게시글");

    printTitle();
    printMenu();

    String menuNo;

    while (true) {
      menuNo = prompt.inputString("메인> ");
      if (menuNo.equals("0")) {
        break;
      } else if (menuNo.equals("menu")) {
        printMenu();
      } else if (menuNo.equals("1")) {
        schduleHandler.execute();
      } else if (menuNo.equals("2")) {
        boardHandler.execute();
      } else {
        System.out.println("올바르지 않은 메뉴 번호입니다.");
      }
    }
    prompt.close();
  }

  static void printMenu() {
    System.out.println("1. 스케줄");
    System.out.println("2. 게시글");

    System.out.println("0. 종료");
  }

  static void printTitle() {
    System.out.println("스케줄 관리 애플리케이션");
    System.out.println("----------------------------------");
  }

}
