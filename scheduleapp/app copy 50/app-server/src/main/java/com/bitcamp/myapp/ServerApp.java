/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
import com.bitcamp.net.NetProtocol;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.Menu;
import com.bitcamp.util.MenuGroup;

public class ServerApp {

  ExecutorService threadPool = Executors.newFixedThreadPool(10);

  final int BOARD_CATEGORY = 1;
  final int READING_CATEGORY = 2;

  Connection con;
  MemberDao memberDao;
  ScheduleDao scheduleDao;
  BoardDao boardDao;
  BoardDao readingDao;

  int port;

  MenuGroup mainMenu = new MenuGroup("메인");

  public ServerApp(int port) throws Exception {

    this.port = port;

    con = DriverManager.getConnection("jdbc:mysql://study:1111@localhost:3306/studydb"
    // JDBC URL
    );

    this.memberDao = new MySQLMemberDao(con);
    this.scheduleDao = new MySQLScheduleDao(con);
    this.boardDao = new MySQLBoardDao(con, BOARD_CATEGORY);
    this.readingDao = new MySQLBoardDao(con, READING_CATEGORY);

    prepareMenu();
  }

  public void close() throws Exception {
    con.close();
  }

  public static void main(String[] args) throws Exception {
    ServerApp app = new ServerApp(8888);
    app.execute();
    app.close();
  }

  public void execute() {
    try (ServerSocket serverSocket = new ServerSocket(this.port)) {
      System.out.println("서버 실행 중...");

      while (true) {

        Socket socket = serverSocket.accept();
        threadPool.execute(() -> processRequest(socket));
      }

    } catch (Exception e) {
      System.out.println("서버 실행 오류!");
      e.printStackTrace();
    }

  }

  private void processRequest(Socket socket) {
    try (Socket s = socket;
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());) {

      BreadcrumbPrompt prompt = new BreadcrumbPrompt(in, out);

      InetSocketAddress clientAdress = (InetSocketAddress) socket.getRemoteSocketAddress();
      System.out.printf("%s 클라이언트 접속함!\n", clientAdress.getHostString());

      out.writeUTF("[나의 목록관리 시스템]\n--------------------------------------------");

      new LoginListener(memberDao).service(prompt);

      mainMenu.execute(prompt);
      out.writeUTF(NetProtocol.NET_END);

    } catch (Exception e) {
      System.out.println("클라이언트 통신 오류!");
      e.printStackTrace();
    }
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
