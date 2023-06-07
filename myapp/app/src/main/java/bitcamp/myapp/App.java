package bitcamp.myapp;

import bitcamp.myapp.handler.MenberHandler;
import bitcamp.util.Prompt;

// 코드 본문에서 사용할 클래스가 어떤 패키지를 클래스인지 지정한다.

public class App {

  public static void main(String[] args) {

    printTitle();

    printMenu();

    while (true) {
      String meneNo = Prompt.inputString("메인> ");
      if (meneNo.equals("6")) {
        break;
      } else if (meneNo.equals("menu")) {
        printMenu();
      } else if (meneNo.equals("1")) {
        MenberHandler.inputMember();
      } else if (meneNo.equals("2")) {
        MenberHandler.printMembers();
      } else if (meneNo.equals("3")) {
        MenberHandler.viewMember();
      } else if (meneNo.equals("4")) {
        MenberHandler.updateMember();
      } else if (meneNo.equals("5")) {
        MenberHandler.deleteMember();
      } else {
        System.out.println(meneNo);
      }

    }

    Prompt.close();
  }

  static void printTitle() {
    System.out.println("나의 목록 관리 시스템");
    System.out.println("------------------------------------");

  }

  static boolean promptContinue() {
    String response = Prompt.inputString("계속 하시겠습니까?(Y/n) ");
    if (!response.equals("") && !response.equalsIgnoreCase("Y") && !response.equalsIgnoreCase("yes")) {
      return false;
    } else {
      return true;
    }
  }

  static void printMenu() {
    System.out.println("1. 회원등록");
    System.out.println("2. 회원목록");
    System.out.println("3. 회원조회");
    System.out.println("4. 회원변경");
    System.out.println("5. 회원삭제");
    System.out.println("6. 종료");
  }

}
