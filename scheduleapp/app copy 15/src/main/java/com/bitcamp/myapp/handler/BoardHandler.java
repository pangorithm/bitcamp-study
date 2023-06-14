package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.vo.Board;
import com.bitcamp.util.Prompt;

public class BoardHandler {
  private static final int MAX_SIZE = 100;

  private Prompt prompt;
  private Board[] boards = new Board[MAX_SIZE];
  private int length = 0;

  public BoardHandler(Prompt prompt) {
    this.prompt = prompt;
  }

  public void inputBoard() {
    Board board = new Board();
    board.setTitle(this.prompt.inputString("제목?\n"));
    board.setContent(this.prompt.inputString("내용?\n"));
    board.setWriter(this.prompt.inputString("작성자\n"));
    board.setPassword(this.prompt.inputString("암호\n"));
    this.boards[this.length++] = board;
  }

  public void printBoards() {
    for (int i = 0; i < this.length; i++) {
      Board board = this.boards[i];
      System.out.printf("번호: %d, 제목: %s, 작성자: %s, 조회수: %d, 작성일: %tY-%5$tm-%5$td\n", board.getNo(),
          board.getTitle(), board.getWriter(), board.getViewCount(), board.getCreatedDate());
    }
  }

  public void viewBoard() {
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

  public void updateBoard() {
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

  public void deleteBoard() {
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
