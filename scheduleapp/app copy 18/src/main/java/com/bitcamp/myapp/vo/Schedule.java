package com.bitcamp.myapp.vo;

public class Schedule {
  private static int scheduleId = 0;

  private int no;
  private String scheduleTitle;
  private long startTime;
  private long endTime;

  public Schedule() {
    this.no = scheduleId++;
  }

  public Schedule(Schedule schedule) {
    this.no = scheduleId++;
    this.scheduleTitle = schedule.scheduleTitle;
    this.startTime = schedule.startTime;
    this.endTime = schedule.endTime;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getScheduleTitle() {
    return scheduleTitle;
  }

  public void setScheduleTitle(String scheduleTitle) {
    this.scheduleTitle = scheduleTitle;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }


}
