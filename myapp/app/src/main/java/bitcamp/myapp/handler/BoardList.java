package bitcamp.myapp.handler;

import bitcamp.myapp.vo.Board;

public class BoardList {
  private static final int MAX_SIZE = 100;

  private Board[] boards = new Board[MAX_SIZE];
  private int length = 0;

  public boolean add(Board board) {
    if (!this.available()) {
      return false;
    }
    this.boards[this.length++] = board;
    return true;
  }

  public Board[] list() {
    Board[] arr = new Board[this.length];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = boards[i];
    }
    return arr;
  }

  public Board get(int no) {
    Board board = this.boards[indexOf(no)];
    if (board.getNo() == no) {
      return board;
    }
    return null;
  }

  public void delete(int no) {

    for (int i = no; i < this.length - 1; i++) {
      this.boards[i] = this.boards[i + 1];
    }

    this.boards[--this.length] = null;

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
