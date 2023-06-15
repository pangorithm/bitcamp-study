package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.vo.Board;
import com.bitcamp.util.Prompt;

public class BoardHandler implements Handler {
  private static final int MAX_SIZE = 100;

  private Prompt prompt;
  private Board[] boards = new Board[MAX_SIZE];
  private int length = 0;
  private String title;

  public BoardHandler(Prompt prompt, String title) {
    this.prompt = prompt;
    this.title = title;
  }

  public void execute() {
    printMenu();

    String menuNo;
    while (true) {
      menuNo = prompt.inputString("게시글> ");
      if (menuNo.equals("0")) {
        break;
      } else if (menuNo.equals("menu")) {
        printMenu();
      } else if (menuNo.equals("1")) {
        this.inputBoard();
      } else if (menuNo.equals("2")) {
        this.printBoards();
      } else if (menuNo.equals("3")) {
        this.viewBoard();
      } else if (menuNo.equals("4")) {
        this.updateBoard();
      } else if (menuNo.equals("5")) {
        this.deleteBoard();
      } else {
        System.out.println("올바르지 않은 메뉴 번호입니다.");
      }
    }
  }

  private void printMenu() {
    System.out.printf("1. %s 등록\n", this.title);
    System.out.printf("2. %s 목록\n", this.title);
    System.out.printf("3. %s 검색\n", this.title);
    System.out.printf("4. %s 변경\n", this.title);
    System.out.printf("5. %s 삭제\n", this.title);
    System.out.println("0. 메인");
  }

  private void inputBoard() {
    Board board = new Board();
    board.setTitle(this.prompt.inputString("제목?\n"));
    board.setContent(this.prompt.inputString("내용?\n"));
    board.setWriter(this.prompt.inputString("작성자\n"));
    board.setPassword(this.prompt.inputString("암호\n"));
    this.boards[this.length++] = board;
  }

  private void printBoards() {
    for (int i = 0; i < this.length; i++) {
      Board board = this.boards[i];
      System.out.printf("번호: %d, 제목: %s, 작성자: %s, 조회수: %d, 작성일: %tY-%5$tm-%5$td\n", board.getNo(),
          board.getTitle(), board.getWriter(), board.getViewCount(), board.getCreatedDate());
    }
  }

  private void viewBoard() {
    int boardIndex = indexOf(this.prompt.inputInt("번호?"));
    if (boardIndex == -1) {
      System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
    } else {
      Board board = this.boards[boardIndex];
      board.setViewCount(board.getViewCount() + 1);
      System.out.printf("제목: %s\n", board.getTitle());
      System.out.printf("내용: %s\n", board.getContent());
      System.out.printf("작성자: %s\n", board.getWriter());
      System.out.printf("조회수: %d\n", board.getViewCount());
      System.out.printf("작성일: %tY-%1$tm-%1$td\n", board.getCreatedDate());
    }
  }

  private void updateBoard() {
    int boardIndex = indexOf(this.prompt.inputInt("번호?"));
    if (boardIndex == -1) {
      System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
    } else {
      Board board = this.boards[boardIndex];
      if (board.getPassword().equals(this.prompt.inputString("암호?"))) {
        board.setTitle(this.prompt.inputString("제목? (%s)\n", board.getTitle()));
        board.setContent(this.prompt.inputString("내용? (%s)\n", board.getContent()));
        board.setCreatedDate(System.currentTimeMillis());
      } else {
        System.out.println("암호가 올바르지 않습니다.");
      }
    }
  }

  private void deleteBoard() {
    int boardIndex = indexOf(this.prompt.inputInt("번호?"));
    if (boardIndex == -1) {
      System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
    } else {
      Board board = this.boards[boardIndex];
      if (board.getPassword().equals(this.prompt.inputString("암호?"))) {
        for (int i = boardIndex; i < this.length - 1; i++) {
          this.boards[i] = this.boards[i + 1];
        }
        this.boards[--this.length] = null;
      } else {
        System.out.println("암호가 올바르지 않습니다.");
      }
    }
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
}
