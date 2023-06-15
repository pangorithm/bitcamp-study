package com.bitcamp.myapp.vo;

public class Board {
  public static int boardId = 1;

  private int no;
  private String title;
  private String content;
  private String writer;
  private String password;

  private int viewCount;
  private long createdDate;

  public Board() {
    this.no = boardId++;
    this.createdDate = System.currentTimeMillis();
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
