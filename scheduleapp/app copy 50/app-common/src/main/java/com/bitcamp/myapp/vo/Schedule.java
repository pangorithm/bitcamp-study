package com.bitcamp.myapp.vo;

import java.io.Serializable;
import java.util.Objects;

public class Schedule implements Serializable {

  private static final long serialVersionUID = 1L;

  public static int scheduleId = 1;

  private int no;
  private String scheduleTitle;
  private long startTime;
  private long endTime;
  private Member owner;

  public Schedule() {}

  @Override
  public int hashCode() {
    return Objects.hash(no);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Schedule other = (Schedule) obj;
    return no == other.no;
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

  public Member getOwner() {
    return owner;
  }

  public void setOwner(Member owner) {
    this.owner = owner;
  }

}
