package bitcamp.myapp;

// 코드 본문에서 사용할 클래스가 어떤 패키지를 클래스인지 지정한다.
// import java.io.InputStream;
import java.util.Scanner;

public class App {

  // InputStream keyboard = System.in;
  // Scanner scanner = new java.util.Scanner(keyboard);
  static Scanner scanner = new java.util.Scanner(System.in);

  static final int MAX_SIZE = 100;

  static int[] no = new int[MAX_SIZE];
  static String[] name = new String[MAX_SIZE];
  static String[] email = new String[MAX_SIZE];
  static String[] password = new String[MAX_SIZE];
  static char[] gender = new char[MAX_SIZE];

  static int userId = 1;
  static int length = 0;

  public static void main(String[] args) {

    printTitle();

    while (length < MAX_SIZE) {

      inputMember();

      if (!promptContinue()) {
        break;
      }
    }

    printMembers();

    scanner.close();
  }

  static void printTitle() {
    System.out.println("나의 목록 관리 시스템");
    System.out.println("--------------------------------------------");
  }

  static void inputMember() {
    System.out.print("이름? ");
    name[length] = scanner.next();

    System.out.print("email? ");
    email[length] = scanner.next();

    System.out.print("비밀번호? ");
    password[length] = scanner.next();

    loop: while (true) {
      System.out.println("성별: ");
      System.out.println("  1. 남자");
      System.out.println("  2. 여자");
      System.out.print(">");
      String menuNo = scanner.next();
      scanner.nextLine(); // 입력 값(token)을 읽고 난 후에 남아 있는 줄바꿈 코드를 제거한다.

      switch (menuNo) {
        case "1":
          gender[length] = 'M';
          // break; // 반복문을 나가는 것이 아니라 switch문을 나간다
          break loop; // loop라는 라벨이 붙은 반복문을 나가라!
        case "2":
          gender[length] = 'W';
          break loop;
        default:
          System.out.println("무효한 번호입니다.");
      }

    }

    no[length] = userId++;
    length++;
  }

  static boolean promptContinue() {
    System.out.print("계속 하시겠습니까?(Y/n) ");
    String response = scanner.nextLine();
    if (!response.equals("") && !response.equalsIgnoreCase("Y") && !response.equalsIgnoreCase("yes")) {
      return false;
    }
    return true;
  }

  static void printMembers() {
    // 회원정보 출력
    System.out.println("--------------------------------------------");
    System.out.println("번호, 이름, 이메일, 성별");
    System.out.println("--------------------------------------------");
    for (int i = 0; i < length; i++) {
      System.out.printf("%d, %s, %s, %c\n", no[i], name[i], email[i], gender[i]);
    }
  }
}
