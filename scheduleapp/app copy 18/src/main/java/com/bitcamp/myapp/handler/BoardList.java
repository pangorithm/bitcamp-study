package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.vo.Board;

public class BoardList {

  private static final int DEFAULT_SIZE = 100;
  private Board[] boards = new Board[DEFAULT_SIZE];
  private int length = 0;

  public void add(Board board) {
    if (length == boards.length) {
      increase();
    }
    this.boards[this.length++] = board;
  }

  private void increase() {
    Board[] arr = new Board[length << 1];
    for (int i = 0; i < length; i++) {
      arr[i] = boards[i];
    }
    boards = arr;
  }

  public Board[] list() {
    Board[] arr = new Board[this.length];
    for (int i = 0; i < this.length; i++) {
      arr[i] = boards[i];
    }
    return arr;
  }

  public Board get(int no) {
    int index = indexOf(no);
    if (index == -1) {
      return null;
    }
    return boards[index];
  }

  public void delete(int boardNo) {
    for (int i = indexOf(boardNo); i < this.length - 1; i++) {
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
}
