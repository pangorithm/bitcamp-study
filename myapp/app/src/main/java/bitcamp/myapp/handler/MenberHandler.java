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

    inputInfo(length);

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

  public static void viewMember() {
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

  public static void updateMember() {

    inputInfo(findMenberIndex());

  }

  public static void deleteMember() {
    for (int i = findMenberIndex(); i < MAX_SIZE - 1; i++) {
      if (no[i] == 0 || i > MAX_SIZE) {
        break;
      }
      no[i] = no[i + 1];
      name[i] = name[i + 1];
      email[i] = email[i + 1];
      password[i] = password[i + 1];
      gender[i] = gender[i + 1];
    }
    no[MAX_SIZE - 1] = 0;
    name[MAX_SIZE - 1] = "";
    email[MAX_SIZE - 1] = "";
    password[MAX_SIZE - 1] = "";
    gender[MAX_SIZE - 1] = (char) 0;

    length--;

  }

  private static boolean available() {
    return length < MAX_SIZE;
  }

  private static void inputInfo(int index) {
    name[index] = Prompt.inputString("이름? ");

    email[index] = Prompt.inputString("email? ");

    password[index] = Prompt.inputString("비밀번호? ");

    gender[index] = inputGender(gender[index]);
  }

  private static char inputGender(char gender) {
    String label;
    if (gender == (char) 0) {
      label = "성별? \n";
    } else {
      label = String.format("성별(%s)?\n", toGenderString(gender));
    }

    loop: while (true) {
      String menuNo = Prompt.inputString(label + "  1. 남자\n" + "  2. 여자\n" + "> ");

      switch (menuNo) {
        case "1":
          return MALE;
        // break; // 반복문을 나가는 것이 아니라 switch문을 나간다
        case "2":
          return FEMALE;
        default:
          System.out.println("무효한 번호입니다.");
      }
    }
  }

  public static String toGenderString(char gender) {
    return (gender == 'M') ? "Male|남자|男" : "Female|여자|女";
  }

  private static int findMenberIndex() {
    String memberNo = Prompt.inputString("번호? ");
    for (int i = 0; i < length; i++) {
      if (no[i] == Integer.parseInt(memberNo)) {
        return i;
      }
    }
    System.out.println("해당 번호의 회원이 없습니다!");
    return -1;
  }
}
