package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.vo.Schedule;

public class ScheduleList {

  private static final int DEFAULT_SIZE = 100;
  private Schedule[] schedules = new Schedule[DEFAULT_SIZE];
  private int length = 0;

  public void add(Schedule schedule) {
    if (length == schedules.length) {
      increase();
    }
    schedules[length++] = new Schedule(schedule);
  }

  private void increase() {
    Schedule[] arr = new Schedule[length << 1];
    for (int i = 0; i < length; i++) {
      arr[i] = schedules[i];
    }
    schedules = arr;
  }

  public Schedule[] list() {
    Schedule[] arr = new Schedule[length];
    for (int i = 0; i < length; i++) {
      arr[i] = schedules[i];
    }
    return arr;
  }

  public Schedule get(int no) {
    int index = indexOf(no);
    if (index == -1) {
      return null;
    }
    return schedules[index];
  }

  public void update(Schedule schedule, int no) {
    schedule.setNo(no);
    int index = indexOf(no);
    if (index == -1) {
      return;
    }
    schedules[index] = schedule;
  }

  public void delete(int no) {
    for (int i = indexOf(no); i < this.length - 1; i++) {
      this.schedules[i] = this.schedules[i + 1];
    }

    this.schedules[this.length - 1] = null;

    this.length--;
  }

  private int indexOf(int no) {
    for (int i = 0; i < length; i++) {
      if (schedules[i].getNo() == no) {
        return i;
      }
    }
    return -1;
  }
}
