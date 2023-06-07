package bitcamp.myapp.handler;

import bitcamp.util.Prompt;

public class MenberHandler {
  public static final int MAX_SIZE = 100;

  static int[] no = new int[MAX_SIZE];
  static String[] name = new String[MAX_SIZE];
  static String[] email = new String[MAX_SIZE];
  static String[] password = new String[MAX_SIZE];
  static char[] gender = new char[MAX_SIZE];

  static int userId = 1;
  public static int length = 0;

  static final char MALE = 'M';
  static final char FEMALE = 'W';

  public static void inputMember() {
    if (!available()) {
      System.out.println("더이상 입력할 수 없습니다.");
      return;
    }
    name[length] = Prompt.inputString("이름? ");

    email[length] = Prompt.inputString("email? ");

    password[length] = Prompt.inputString("비밀번호? ");

    loop: while (true) {
      String menuNo = Prompt.inputString("성별: \n" + "  1. 남자\n" + "  2. 여자\n" + "> ");

      switch (menuNo) {
        case "1":
          gender[length] = MALE;
          // break; // 반복문을 나가는 것이 아니라 switch문을 나간다
          break loop; // loop라는 라벨이 붙은 반복문을 나가라!
        case "2":
          gender[length] = FEMALE;
          break loop;
        default:
          System.out.println("무효한 번호입니다.");
      }

    }

    no[length] = userId++;
    length++;
  }

  public static void printMembers() {
    // 회원정보 출력
    System.out.println("--------------------------------------------");
    System.out.println("번호, 이름, 이메일, 성별");
    System.out.println("--------------------------------------------");
    for (int i = 0; i < length; i++) {
      System.out.printf("%d, %s, %s, %s\n", no[i], name[i], email[i],
          toGenderString(gender[i]));
    }
  }

  public static void viewMembers() {
    String memberNo = Prompt.inputString("번호? ");
    for (int i = 0; i < length; i++) {
      if (no[i] == Integer.parseInt(memberNo)) {
        System.out.printf("이름: %s\n", name[i]);
        System.out.printf("email: %s\n", email[i]);
        System.out.printf("성별: %s\n", toGenderString(gender[i]));
        return;
      }
    }
    System.out.println("해당 번호의 회원이 없습니다!");
  }

  public static void updateMembers() {
    String memberNo = Prompt.inputString("번호? ");
    int memberNoInt = Integer.parseInt(memberNo);

    for (int i = 0; i < length; i++) {
      if (no[i] == memberNoInt) {
        name[memberNoInt] = Prompt.inputString("이름? ");

        email[memberNoInt] = Prompt.inputString("email? ");

        password[memberNoInt] = Prompt.inputString("비밀번호? ");
        loop: while (true) {
          String menuNo = Prompt.inputString("성별: \n" + "  1. 남자\n" + "  2. 여자\n" + "> ");

          switch (menuNo) {
            case "1":
              gender[length] = MALE;
              // break; // 반복문을 나가는 것이 아니라 switch문을 나간다
              break loop; // loop라는 라벨이 붙은 반복문을 나가라!
            case "2":
              gender[length] = FEMALE;
              break loop;
            default:
              System.out.println("무효한 번호입니다.");
          }
        }
        return;
      }
    }
    System.out.println("해당 번호의 회원이 없습니다!");
  }

  private static boolean available() {
    return length < MAX_SIZE;
  }

  public static String toGenderString(char gender) {
    return (gender == 'M') ? "Male|남자|男" : "Female|여자|女";
  }
  // public static String toGenderString(char gender, String lang) {
  // String[] male = { "male", "남자", "男" };
  // String[] female = { "Female", "여자", "女" };
  // int index = 0;
  // switch (lang) {
  // case "ko":
  // index = 1;
  // break;
  // case "cn":
  // index = 2;
  // break;
  // default:
  // break;
  // }
  // }
}
