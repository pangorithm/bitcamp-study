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
    ScheduleHandler schduleHandler = new ScheduleHandler(prompt);
    BoardHandler boardHandler = new BoardHandler(prompt);

    printTitle();
    printMenu();

    String menuNo;

    while (true) {
      menuNo = prompt.inputString("메인> ");
      if (menuNo.equals("99")) {
        break;
      } else if (menuNo.equals("menu")) {
        printMenu();
      } else if (menuNo.equals("1")) {
        schduleHandler.inputSchedule();
      } else if (menuNo.equals("2")) {
        schduleHandler.printSchedules();
      } else if (menuNo.equals("3")) {
        schduleHandler.searchSchedules();
      } else if (menuNo.equals("4")) {
        schduleHandler.updateSchedule();
      } else if (menuNo.equals("5")) {
        schduleHandler.deleteSchdule();
      } else if (menuNo.equals("6")) {
        boardHandler.inputBoard();
      } else if (menuNo.equals("7")) {
        boardHandler.printBoards();
      } else if (menuNo.equals("8")) {
        boardHandler.viewBoard();
      } else if (menuNo.equals("9")) {
        boardHandler.updateBoard();
      } else if (menuNo.equals("10")) {
        boardHandler.deleteBoard();
      } else {
        System.out.println("올바르지 않은 메뉴 번호입니다.");
      }
    }
    prompt.close();
  }

  static void printMenu() {
    System.out.println("1. 스케줄 등록");
    System.out.println("2. 스케줄 목록");
    System.out.println("3. 스케줄 검색");
    System.out.println("4. 스케줄 변경");
    System.out.println("5. 스케줄 삭제");

    System.out.println("6. 게시글 등록");
    System.out.println("7. 게시글 목록");
    System.out.println("8. 게시글 검색");
    System.out.println("9. 게시글 변경");
    System.out.println("10. 게시글 삭제");

    System.out.println("99. 종료");
  }

  static void printTitle() {
    System.out.println("스케줄 관리 애플리케이션");
    System.out.println("----------------------------------");
  }

}