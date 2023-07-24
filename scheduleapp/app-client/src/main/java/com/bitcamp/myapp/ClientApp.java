/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.bitcamp.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import com.bitcamp.dao.MySQLBoardDao;
import com.bitcamp.dao.MySQLMemberDao;
import com.bitcamp.dao.MySQLScheduleDao;
import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.handler.BoardAddListener;
import com.bitcamp.myapp.handler.BoardDeleteListener;
import com.bitcamp.myapp.handler.BoardDetailListener;
import com.bitcamp.myapp.handler.BoardListListener;
import com.bitcamp.myapp.handler.BoardUpdateListener;
import com.bitcamp.myapp.handler.FooterListener;
import com.bitcamp.myapp.handler.HeaderListener;
import com.bitcamp.myapp.handler.HelloListener;
import com.bitcamp.myapp.handler.LoginListener;
import com.bitcamp.myapp.handler.MemberAddListener;
import com.bitcamp.myapp.handler.MemberDeleteListener;
import com.bitcamp.myapp.handler.MemberDetailListener;
import com.bitcamp.myapp.handler.MemberListListener;
import com.bitcamp.myapp.handler.MemberUpdateListener;
import com.bitcamp.myapp.handler.ScheduleAddListener;
import com.bitcamp.myapp.handler.ScheduleDeleteListener;
import com.bitcamp.myapp.handler.ScheduleDetailListener;
import com.bitcamp.myapp.handler.ScheduleListListener;
import com.bitcamp.myapp.handler.ScheduleSearchListener;
import com.bitcamp.myapp.handler.ScheduleUpdateListener;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.Menu;
import com.bitcamp.util.MenuGroup;

public class ClientApp {

  final int BOARD_CATEGORY = 1;
  final int READING_CATEGORY = 2;

  public static Member loginUser;

  MemberDao memberDao;
  ScheduleDao scheduleDao;
  BoardDao boardDao;
  BoardDao readingDao;

  BreadcrumbPrompt prompt = new BreadcrumbPrompt();

  MenuGroup mainMenu = new MenuGroup("메인");

  public ClientApp(String ip, int port) throws Exception {

    Connection con = DriverManager.getConnection("jdbc:mysql://study:1111@localhost:3306/studydb"
    // JDBC URL
    );

    this.memberDao = new MySQLMemberDao(con);
    this.scheduleDao = new MySQLScheduleDao(con);
    this.boardDao = new MySQLBoardDao(con, BOARD_CATEGORY);
    this.readingDao = new MySQLBoardDao(con, READING_CATEGORY);
    prepareMenu();
  }

  public void close() throws Exception {
    prompt.close();
  }

  public static void main(String[] args) throws Exception {
    ClientApp app = new ClientApp(args[0], Integer.parseInt(args[1]));
    app.execute();
    app.close();
  }

  static void printTitle() {
    System.out.println("스케줄 관리 애플리케이션");
    System.out.println("----------------------------------");
  }

  public void execute() {

    printTitle();

    new LoginListener(memberDao).service(prompt);

    mainMenu.execute(prompt);

  }

  private void prepareMenu() {
    MenuGroup memberMenu = new MenuGroup("회원");
    memberMenu.add(new Menu("등록", new MemberAddListener(memberDao)));
    memberMenu.add(new Menu("목록", new MemberListListener(memberDao)));
    memberMenu.add(new Menu("조회", new MemberDetailListener(memberDao)));
    memberMenu.add(new Menu("변경", new MemberUpdateListener(memberDao)));
    memberMenu.add(new Menu("삭제", new MemberDeleteListener(memberDao)));
    mainMenu.add(memberMenu);

    MenuGroup scheduleMenu = new MenuGroup("스케줄");
    scheduleMenu.add(new Menu("등록", new ScheduleAddListener(scheduleDao)));
    scheduleMenu.add(new Menu("목록", new ScheduleListListener(scheduleDao)));
    scheduleMenu.add(new Menu("검색", new ScheduleSearchListener(scheduleDao)));
    scheduleMenu.add(new Menu("조회", new ScheduleDetailListener(scheduleDao)));
    scheduleMenu.add(new Menu("변경", new ScheduleUpdateListener(scheduleDao)));
    scheduleMenu.add(new Menu("삭제", new ScheduleDeleteListener(scheduleDao)));
    mainMenu.add(scheduleMenu);


    MenuGroup boardMenu = new MenuGroup("게시글");
    boardMenu.add(new Menu("등록", new BoardAddListener(boardDao)));
    boardMenu.add(new Menu("목록", new BoardListListener(boardDao)));
    boardMenu.add(new Menu("조회", new BoardDetailListener(boardDao)));
    boardMenu.add(new Menu("변경", new BoardUpdateListener(boardDao)));
    boardMenu.add(new Menu("삭제", new BoardDeleteListener(boardDao)));
    mainMenu.add(boardMenu);

    MenuGroup readingMenu = new MenuGroup("독서록");
    readingMenu.add(new Menu("등록", new BoardAddListener(readingDao)));
    readingMenu.add(new Menu("목록", new BoardListListener(readingDao)));
    readingMenu.add(new Menu("조회", new BoardDetailListener(readingDao)));
    readingMenu.add(new Menu("변경", new BoardUpdateListener(readingDao)));
    readingMenu.add(new Menu("삭제", new BoardDeleteListener(readingDao)));
    mainMenu.add(readingMenu);

    Menu helloMenu = new Menu("안녕!");
    helloMenu.addActionListener(new HeaderListener());
    helloMenu.addActionListener(new HelloListener());
    helloMenu.addActionListener(new FooterListener());
    mainMenu.add(helloMenu);
  }

}
