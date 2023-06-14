package bitcamp.myapp.handler;

import bitcamp.myapp.vo.Board;
import bitcamp.util.Prompt;

public class BoardHandler {

  // 인스턴스에 상관없이 공통으로 사용하는 필드라면 스태틱 필드로 선언한다.
  private static int MAX_SIZE = 100;

  // 인스턴스 마다 별개로 관리해야 할 데이터라면 논스태틱 필드(인스턴스 필드)로 선언한다.
  private Prompt prompt;
  private Board[] boards = new Board[MAX_SIZE];
  private int length = 0;

  public BoardHandler(Prompt prompt) {
    this.prompt = prompt;
  }

  // 인스턴스 멤버(필드나 메서드)를 사용하는 경우 인스턴스 메서드로 정의해야 한다.
  public void inputBoard() {
    if (!this.available()) {
      System.out.println("더이상 입력할 수 없습니다!");
      return;
    }

    Board board = new Board();
    board.setTitle(this.prompt.inputString("제목? "));
    board.setContent(this.prompt.inputString("이메일? "));
    board.setWriter(this.prompt.inputString("작성자? "));
    board.setPassword(this.prompt.inputString("암호? "));

    // 위에서 만든 board 인스턴스의 주소를 잃어버리지 않게
    // 레퍼런스 배열에 담는다.

    this.boards[this.length++] = board;
  }

  public void printBoards() {
    System.out.println("---------------------------------------");
    System.out.println("번호, 제목, 작성자, 조회수, 작성일");
    System.out.println("---------------------------------------");


    for (int i = 0; i < length; i++) {
      Board board = this.boards[i];

      System.out.printf("%d, %s, %s, %d, %tY-%5$tm-%5$td\n", board.getNo(), board.getTitle(),
          board.getWriter(), board.getViewCount(), board.getCreatedDate());

      boards[i] = board;
    }
  }

  public void viewBoard() {
    String boardNo = this.prompt.inputString("번호? ");
    for (int i = 0; i < this.length; i++) {
      Board board = boards[i];
      if (board.getNo() == Integer.parseInt(boardNo)) {
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

  public void updateBoard() {
    String boardNo = this.prompt.inputString("번호? ");
    for (int i = 0; i < this.length; i++) {
      Board board = this.boards[i];
      if (board.getNo() == Integer.parseInt(boardNo)) {
        if (Prompt.inputString("암호? ").equals(board.getPassword())) {
          board.setTitle(this.prompt.inputString("제목(%s)? \n> ", board.getTitle()));
          board.setContent(this.prompt.inputString("내용(%s)? \n> ", board.getContent()));
          board.setCreatedDate(System.currentTimeMillis());
          return;
        } else {
          System.out.println("암호가 올바르지 않습니다");
          return;
        }
      }
    }
    System.out.println("해당 번호의 게시글이 없습니다!");
  }

  public void deleteBoard() {
    int deletedIndex = indexOf(this.prompt.inputInt("번호? "));
    if (deletedIndex == -1) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }
    String checkpw = this.prompt.inputString("암호? ");
    if (!checkpw.equals(this.boards[deletedIndex].getPassword())) {
      System.out.println("암호가 올바르지 않습니다");
      return;
    }

    for (int i = deletedIndex; i < this.length - 1; i++) {
      this.boards[i] = this.boards[i + 1];
    }

    this.boards[this.length-- - 1] = null;
  }

  private int indexOf(int boardNo) {
    for (int i = 0; i < this.length; i++) {
      Board board = this.boards[i];
      if (board.getNo() == boardNo) {
        return i;
      }
    }
    return -1;
  }

  private boolean available() {
    return this.length < MAX_SIZE;
  }
}
