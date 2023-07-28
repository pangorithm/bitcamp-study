package bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.MySQLBoardDao;
import bitcamp.myapp.dao.MySQLMemberDao;
import bitcamp.myapp.handler.BoardAddListener;
import bitcamp.myapp.handler.BoardDeleteListener;
import bitcamp.myapp.handler.BoardDetailListener;
import bitcamp.myapp.handler.BoardListListener;
import bitcamp.myapp.handler.BoardUpdateListener;
import bitcamp.myapp.handler.LoginListener;
import bitcamp.myapp.handler.MemberAddListener;
import bitcamp.myapp.handler.MemberDeleteListener;
import bitcamp.myapp.handler.MemberDetailListener;
import bitcamp.myapp.handler.MemberListListener;
import bitcamp.myapp.handler.MemberUpdateListener;
import bitcamp.net.NetProtocol;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.Menu;
import bitcamp.util.MenuGroup;
import bitcamp.util.SqlSessionFactoryProxy;

public class ServerApp {

  ExecutorService threadPool = Executors.newFixedThreadPool(10);

  SqlSessionFactory sqlSessionFactory;

  MemberDao memberDao;
  BoardDao boardDao;

  private static final int BOARD_CATEGORY = 1;
  private static final int READING_CATEGORY = 2;

  MenuGroup mainMenu = new MenuGroup("메인");

  int port;

  public ServerApp(int port) throws Exception {

    this.port = port;

    // 1) mybatis 설정 파일을 읽어들일 도구를 준비한다.
    InputStream mybatisConfigIn =
        Resources.getResourceAsStream("bitcamp/myapp/config/mybatis-config.xml");

    // 2) SqlSessionFactory를 만들어 줄 빌더 객체 준비
    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

    // 3) 빌더 객체를 통해 를 SqlSessionFactory를생성
    sqlSessionFactory = new SqlSessionFactoryProxy(builder.build(mybatisConfigIn));

    this.memberDao = new MySQLMemberDao(sqlSessionFactory);
    this.boardDao = new MySQLBoardDao(sqlSessionFactory);

    prepareMenu();
  }

  public void close() throws Exception {

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

    } finally {
      ((SqlSessionFactoryProxy) sqlSessionFactory).clean();
    }
  }

  private void prepareMenu() {
    MenuGroup memberMenu = new MenuGroup("회원");
    memberMenu.add(new Menu("등록", new MemberAddListener(memberDao, sqlSessionFactory)));
    memberMenu.add(new Menu("목록", new MemberListListener(memberDao)));
    memberMenu.add(new Menu("조회", new MemberDetailListener(memberDao)));
    memberMenu.add(new Menu("변경", new MemberUpdateListener(memberDao, sqlSessionFactory)));
    memberMenu.add(new Menu("삭제", new MemberDeleteListener(memberDao, sqlSessionFactory)));
    mainMenu.add(memberMenu);

    MenuGroup boardMenu = new MenuGroup("게시글");
    boardMenu
        .add(new Menu("등록", new BoardAddListener(BOARD_CATEGORY, boardDao, sqlSessionFactory)));
    boardMenu.add(new Menu("목록", new BoardListListener(BOARD_CATEGORY, boardDao)));
    boardMenu
        .add(new Menu("조회", new BoardDetailListener(BOARD_CATEGORY, boardDao, sqlSessionFactory)));
    boardMenu
        .add(new Menu("변경", new BoardUpdateListener(BOARD_CATEGORY, boardDao, sqlSessionFactory)));
    boardMenu
        .add(new Menu("삭제", new BoardDeleteListener(BOARD_CATEGORY, boardDao, sqlSessionFactory)));
    mainMenu.add(boardMenu);

    MenuGroup readingMenu = new MenuGroup("독서록");
    readingMenu
        .add(new Menu("등록", new BoardAddListener(READING_CATEGORY, boardDao, sqlSessionFactory)));
    readingMenu.add(new Menu("목록", new BoardListListener(READING_CATEGORY, boardDao)));
    readingMenu.add(
        new Menu("조회", new BoardDetailListener(READING_CATEGORY, boardDao, sqlSessionFactory)));
    readingMenu.add(
        new Menu("변경", new BoardUpdateListener(READING_CATEGORY, boardDao, sqlSessionFactory)));
    readingMenu.add(
        new Menu("삭제", new BoardDeleteListener(READING_CATEGORY, boardDao, sqlSessionFactory)));
    mainMenu.add(readingMenu);

    // Menu helloMenu = new Menu("안녕!");
    // helloMenu.addActionListener(new HeaderListener());
    // helloMenu.addActionListener(new HelloListener());
    // helloMenu.addActionListener(new FooterListener());
    // mainMenu.add(helloMenu);
  }

}
