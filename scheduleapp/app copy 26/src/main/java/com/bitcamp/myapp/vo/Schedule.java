package com.bitcamp.myapp.vo;

public class Schedule {
  private static int scheduleId = 1;

  private int no;
  private String scheduleTitle;
  private long startTime;
  private long endTime;

  public Schedule() {
    this.no = scheduleId++;
  }

  public Schedule(int no) {
    this.no = no;
  }

  public Schedule(Schedule sch) {
    this.no = sch.no;
    this.scheduleTitle = sch.scheduleTitle;
    this.startTime = sch.startTime;
    this.endTime = sch.endTime;
  }

  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    Schedule sch = (Schedule) obj;
    if (this.getNo() != sch.getNo()) {
      return false;
    }
    return true;
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
