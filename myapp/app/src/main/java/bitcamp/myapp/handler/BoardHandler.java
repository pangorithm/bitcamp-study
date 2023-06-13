package bitcamp.myapp.handler;

import bitcamp.myapp.vo.Board;
import bitcamp.util.Prompt;

public class BoardHandler {

  static final int MAX_SIZE = 100;

  static Board[] members = new Board[MAX_SIZE];

  static int length = 0;

  public static void inputBoard() {
    if (!available()) {
      System.out.println("더이상 입력할 수 없습니다!");
      return;
    }

    Board board = new Board();
    board.setTitle(Prompt.inputString("제목? "));
    board.setContent(Prompt.inputString("이메일? "));
    board.setWriter(Prompt.inputString("작성자? "));
    board.setPassword(Prompt.inputString("암호? "));

    // 위에서 만든 member 인스턴스의 주소를 잃어버리지 않게
    // 레퍼런스 배열에 담는다.

    members[length] = board;
    length++;
  }

  public static void printBoards() {
    System.out.println("---------------------------------------");
    System.out.println("번호, 제목, 작성자, 조회수, 작성일");
    System.out.println("---------------------------------------");


    for (int i = 0; i < length; i++) {
      Board board = members[i];

      System.out.printf("%d, %s, %s, %d, %tY-%5$tm-%5$td\n", board.getNo(), board.getTitle(),
          board.getWriter(), board.getViewCount(), board.getCreatedDate());

      members[i] = board;
    }
  }

  public static void viewBoard() {
    String memberNo = Prompt.inputString("번호? ");
    for (int i = 0; i < length; i++) {
      Board board = members[i];
      if (board.getNo() == Integer.parseInt(memberNo)) {
        board.setViewCount(board.getViewCount() + 1);
        System.out.printf("제목: %s\n", board.getTitle());
        System.out.printf("작성자: %s\n", board.getWriter());
        System.out.printf("내용: %s\n", board.getContent());
        System.out.printf("조회수: %d\n", board.getViewCount());
        System.out.printf("작성일: %tY-%1$tm-%1$td\n", board.getCreatedDate());
        return;
      }
    }
    System.out.println("해당 번호의 게시글이 없습니다!");
  }

  public static void updateBoard() {
    String memberNo = Prompt.inputString("번호? ");
    for (int i = 0; i < length; i++) {
      Board board = members[i];
      if (board.getNo() == Integer.parseInt(memberNo)) {
        String checkpw = Prompt.inputString("암호? ");
        if (checkpw.equals(board.getPassword())) {
          System.out.printf("제목(%s)? \n> ", board.getTitle());
          board.setTitle(Prompt.inputString(""));
          System.out.printf("내용(%s)? \n> ", board.getContent());
          board.setContent(Prompt.inputString(""));
          board.setCreatedDate(System.currentTimeMillis());
          return;
        } else {
          System.out.println("암호가 올바르지 않습니다");
        }
      }
    }
    System.out.println("해당 번호의 게시글이 없습니다!");
  }

  public static void deleteBoard() {
    int memberNo = Prompt.inputInt("번호? ");

    int deletedIndex = indexOf(memberNo);
    if (deletedIndex == -1) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }
    String checkpw = Prompt.inputString("암호? ");
    if (!checkpw.equals(members[deletedIndex].getPassword())) {
      System.out.println("암호가 올바르지 않습니다");
      return;
    }

    for (int i = deletedIndex; i < length - 1; i++) {
      members[i] = members[i + 1];
    }

    members[length - 1] = null;
    length--;
  }

  private static int indexOf(int memberNo) {
    for (int i = 0; i < length; i++) {
      Board board = members[i];
      if (board.getNo() == memberNo) {
        return i;
      }
    }
    return -1;
  }

  private static boolean available() {
    return length < MAX_SIZE;
  }
}
