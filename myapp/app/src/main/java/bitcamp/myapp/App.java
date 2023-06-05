package bitcamp.myapp;

// 코드 본문에서 사용할 클래스가 어떤 패키지를 클래스인지 지정한다.

public class App {

  public static void main(String[] args) {

    printTitle();

    while (MenberHandler.length < MenberHandler.MAX_SIZE) {

      MenberHandler.inputMember();

      if (!promptContinue()) {
        break;
      }
    }

    MenberHandler.printMembers();

    Prompt.scanner.close();
  }

  static void printTitle() {
    System.out.println("나의 목록 관리 시스템");
    System.out.println("--------------------------------------------");
  }

  static boolean promptContinue() {
    String response = Prompt.inputString("계속 하시겠습니까?(Y/n) ");
    if (!response.equals("") && !response.equalsIgnoreCase("Y") && !response.equalsIgnoreCase("yes")) {
      return false;
    } else {
      return true;
    }
  }

}
