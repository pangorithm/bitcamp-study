package com.bitcamp.myapp.vo;

import java.io.Serializable;

public class Board implements Serializable, CsvObject, AutoIncrement {

  private static final long serialVersionUID = 1L;

  public static int boardId = 1;

  private int no;
  private String title;
  private String content;
  private String writer;
  private String password;

  private int viewCount;
  private long createdDate;

  public Board() {}

  public Board(int no) {
    this.no = no;
  }

  @Override
  public void updateKey() {
    if (this.no > Board.boardId) {
      Board.boardId = this.no + 1;
    }
  }

  public static Board fromCsv(String scv) {
    String[] values = scv.split(",");

    Board board = new Board(Integer.parseInt(values[0]));
    board.setTitle(values[1]);
    board.setContent(values[2]);
    board.setWriter(values[3]);
    board.setPassword(values[4]);
    board.setViewCount(Integer.parseInt(values[5]));
    board.setCreatedDate(Long.parseLong(values[6]));

    if (Board.boardId <= board.getNo()) {
      Board.boardId = board.getNo() + 1;
    }

    return board;
  }

  @Override
  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%d,%d\n", this.getNo(), this.getTitle(), this.getContent(),
        this.getWriter(), this.getPassword(), this.getViewCount(), this.getCreatedDate());
  }

  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    Board sch = (Board) obj;
    if (this.getNo() != sch.getNo()) {
      return false;
    }
    return true;
  }

  public static int getScheduleId() {
    return boardId;
  }

  public static void setScheduleId(int boardId) {
    Board.boardId = boardId;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getWriter() {
    return writer;
  }

  public void setWriter(String writer) {
    this.writer = writer;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getViewCount() {
    return viewCount;
  }

  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }

  public long getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(long createdDate) {
    this.createdDate = createdDate;
  }

}
