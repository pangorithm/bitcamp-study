/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.bitcamp.myapp;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.bitcamp.myapp.handler.BoardAddListener;
import com.bitcamp.myapp.handler.BoardDeleteListener;
import com.bitcamp.myapp.handler.BoardDetailListener;
import com.bitcamp.myapp.handler.BoardListListener;
import com.bitcamp.myapp.handler.BoardUpdateListener;
import com.bitcamp.myapp.handler.FooterListener;
import com.bitcamp.myapp.handler.HeaderListener;
import com.bitcamp.myapp.handler.HelloListener;
import com.bitcamp.myapp.handler.ScheduleAddListener;
import com.bitcamp.myapp.handler.ScheduleDeleteListener;
import com.bitcamp.myapp.handler.ScheduleListListener;
import com.bitcamp.myapp.handler.ScheduleSearchListener;
import com.bitcamp.myapp.handler.ScheduleUpdateListener;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.Menu;
import com.bitcamp.util.MenuGroup;

public class App {

  ArrayList<Schedule> scheduleList = new ArrayList<>();
  LinkedList<Board> boardList = new LinkedList<>();
  LinkedList<Board> readingList = new LinkedList<>();

  BreadcrumbPrompt prompt = new BreadcrumbPrompt();

  MenuGroup mainMenu = new MenuGroup("메인");

  public App() {
    prepareMenu();
  }

  public static void main(String[] args) {
    new App().execute();
  }

  static void printTitle() {
    System.out.println("스케줄 관리 애플리케이션");
    System.out.println("----------------------------------");
  }

  static String getMenu() {
    StringBuilder menu = new StringBuilder();
    menu.append("1. 스케줄\n");
    menu.append("2. 게시글\n");
    menu.append("0. 종료\n");

    return menu.toString();
  }

  public void execute() {

    printTitle();
    loadData();
    mainMenu.execute(prompt);
    saveData();
    prompt.close();
  }

  private void prepareMenu() {
    MenuGroup memberMenu = new MenuGroup("스캐줄");
    memberMenu.add(new Menu("등록", new ScheduleAddListener(scheduleList)));
    memberMenu.add(new Menu("목록", new ScheduleListListener(scheduleList)));
    memberMenu.add(new Menu("검색", new ScheduleSearchListener(scheduleList)));
    memberMenu.add(new Menu("변경", new ScheduleUpdateListener(scheduleList)));
    memberMenu.add(new Menu("삭제", new ScheduleDeleteListener(scheduleList)));
    mainMenu.add(memberMenu);


    MenuGroup boardMenu = new MenuGroup("게시글");
    boardMenu.add(new Menu("등록", new BoardAddListener(boardList)));
    boardMenu.add(new Menu("목록", new BoardListListener(boardList)));
    boardMenu.add(new Menu("조회", new BoardDetailListener(boardList)));
    boardMenu.add(new Menu("변경", new BoardUpdateListener(boardList)));
    boardMenu.add(new Menu("삭제", new BoardDeleteListener(boardList)));
    mainMenu.add(boardMenu);

    MenuGroup readingMenu = new MenuGroup("독서록");
    readingMenu.add(new Menu("등록", new BoardAddListener(readingList)));
    readingMenu.add(new Menu("목록", new BoardListListener(readingList)));
    readingMenu.add(new Menu("조회", new BoardDetailListener(readingList)));
    readingMenu.add(new Menu("변경", new BoardUpdateListener(readingList)));
    readingMenu.add(new Menu("삭제", new BoardDeleteListener(readingList)));
    mainMenu.add(readingMenu);

    // Handler memberHandler = new MemberHandler(prompt, "회원", new ArrayList());
    // Handler boardHandler = new BoardDeleteListener(prompt, "게시글", new LinkedList());
    // Handler readingHandler = new BoardDeleteListener(prompt, "독서록", new LinkedList());

    Menu helloMenu = new Menu("안녕!");
    helloMenu.addActionListener(new HeaderListener());
    helloMenu.addActionListener(new HelloListener());
    helloMenu.addActionListener(new FooterListener());
    mainMenu.add(helloMenu);
  }

  public void loadData() {
    loadSchedule("schedule.csv", scheduleList);
    loadBoard("board.csv", boardList);
    loadBoard("reading.csv", readingList);
  }

  public void saveData() {
    saveSchedule("schedule.csv", scheduleList);
    saveBoard("board.csv", boardList);
    saveBoard("reading.csv", readingList);
  }

  public void loadSchedule(String fileName, List<Schedule> list) {
    try {
      FileReader in0 = new FileReader(fileName);
      BufferedReader in = new BufferedReader(in0);

      String line = null;

      while ((line = in.readLine()) != null) {
        String[] values = line.split(",");

        Schedule schedule = new Schedule(Integer.parseInt(values[0]));
        schedule.setScheduleTitle(values[1]);
        schedule.setStartTime(Long.parseLong(values[2]));
        schedule.setEndTime(Long.parseLong(values[3]));

        list.add(schedule);
      }

      if (list.size() > 0) {
        Schedule.scheduleId = Math.max(Schedule.scheduleId, list.get(list.size() - 1).getNo() + 1);
      }

      in.close();

    } catch (Exception e) {
      System.out.println(fileName + "파일을 읽는 중 오류 발생");
    }
  }


  private void loadBoard(String fileName, List<Board> list) {
    try {
      FileReader in0 = new FileReader(fileName);
      BufferedReader in = new BufferedReader(in0);

      String line = null;

      while ((line = in.readLine()) != null) {
        String[] values = line.split(",");

        Board board = new Board(Integer.parseInt(values[0]));
        board.setTitle(values[1]);
        board.setContent(values[2]);
        board.setWriter(values[3]);
        board.setPassword(values[4]);
        board.setViewCount(Integer.parseInt(values[5]));
        board.setCreatedDate(Long.parseLong(values[6]));

        list.add(board);
      }

      if (list.size() > 0) {
        Board.boardId = Math.max(Board.boardId, list.get(list.size() - 1).getNo() + 1);
      }

      in.close();

    } catch (Exception e) {
      System.out.println(fileName + " 파일을 읽는 중 오류 발생!");
    }
  }

  public void saveSchedule(String fileName, List<Schedule> list) {
    try {
      FileOutputStream out0 = new FileOutputStream(fileName);
      BufferedOutputStream out1 = new BufferedOutputStream(out0);
      PrintWriter out = new PrintWriter(out1);

      for (Schedule schedule : list) {
        out.printf("%d,%s,%d,%d\n", schedule.getNo(), schedule.getScheduleTitle(),
            schedule.getStartTime(), schedule.getEndTime());
      }

      out.close();
    } catch (Exception e) {
      System.out.println(fileName + "파일 저장 중 오류 발생");
    }
  }


  private void saveBoard(String fileName, List<Board> list) {
    try {
      FileOutputStream out0 = new FileOutputStream(fileName);
      BufferedOutputStream out1 = new BufferedOutputStream(out0);
      PrintWriter out = new PrintWriter(out1);

      for (Board board : list) {
        out.printf("%d,%s,%s,%s,%s,%d,%d\n", board.getNo(), board.getTitle(), board.getContent(),
            board.getWriter(), board.getPassword(), board.getViewCount(), board.getCreatedDate());
      }

      out.close();

    } catch (Exception e) {
      System.out.println(fileName + " 파일을 저장하는 중 오류 발생!");
    }
  }

}