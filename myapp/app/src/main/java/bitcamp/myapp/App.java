package bitcamp.myapp;

import bitcamp.myapp.handler.BoardHandler;
import bitcamp.myapp.handler.Handler;
import bitcamp.myapp.handler.MemberHandler;
import bitcamp.util.ArrayList;
import bitcamp.util.LinkedList;
import bitcamp.util.MenuPrompt;

public class App {

  public static void main(String[] args) {

    // 기본 생성자를 이용해 Prompt 인스턴스를 준비한다.
    // => 기본 생성자는 Scanner를 키보드와 연결한다. OK
    MenuPrompt prompt = new MenuPrompt();

    prompt.appendBreadcrumb("메인", getMenu());
    // 모든 핸들러는 Handler 규칙에 따라 정의되었기 때문에
    // Handler 레퍼런스에 그 주소를 담을 수 있다.
    Handler memberHandler = new MemberHandler(prompt, "회원", new ArrayList());
    Handler boardHandler = new BoardHandler(prompt, "게시글", new LinkedList());
    Handler readingHandler = new BoardHandler(prompt, "독서록", new LinkedList());

    printTitle();

    prompt.printMenu();

    while (true) {
      String menuNo = prompt.inputMenu();
      if (menuNo.equals("0")) {
        break;
      } else if (menuNo.equals("menu")) {
        prompt.printMenu();
      } else if (menuNo.equals("1")) {
        memberHandler.execute();
      } else if (menuNo.equals("2")) {
        boardHandler.execute();
      } else if (menuNo.equals("3")) {
        readingHandler.execute();
      } else {
        System.out.println("메뉴 번호가 옳지 않습니다!");
      }
    }

    prompt.close();
  }

  static String getMenu() {
    StringBuilder menu = new StringBuilder();
    menu.append("1. 회원\n");
    menu.append("2. 게시글\n");
    menu.append("3. 독서록\n");
    menu.append("0. 종료\n");
    return menu.toString();
  }

  static void printTitle() {
    System.out.println("나의 목록 관리 시스템");
    System.out.println("----------------------------------");
  }
}
