package com.bitcamp.myapp.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Schedule implements Serializable {

  private static final long serialVersionUID = 1L;

  public static int scheduleId = 1;

  private int no;
  private String title;
  private String content;
  private Timestamp startTime;
  private Timestamp endTime;
  private Member owner;
  private List<Member> participantList;

  public Schedule() {
  }

  @Override
  public int hashCode() {
    return Objects.hash(no);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Schedule other = (Schedule) obj;
    return no == other.no;
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

  public void setTitle(String scheduleTitle) {
    this.title = scheduleTitle;
  }

  public Timestamp getStartTime() {
    return startTime;
  }

  public void setStartTime(Timestamp startTime) {
    this.startTime = startTime;
  }

  public Timestamp getEndTime() {
    return endTime;
  }

  public void setEndTime(Timestamp endTime) {
    this.endTime = endTime;
  }

  public Member getOwner() {
    return owner;
  }

  public void setOwner(Member owner) {
    this.owner = owner;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public List<Member> getParticipantList() {
    return participantList;
  }

  public void setParticipantList(List<Member> participantList) {
    this.participantList = participantList;
  }
}
