package com.bitcamp.myapp.dao;

import java.util.LinkedList;
import java.util.List;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.JsonDataHelper;

public class ScheduleListDao implements ScheduleDao {

  String filename;
  LinkedList<Schedule> list = new LinkedList<>();

  public ScheduleListDao(String filename) {
    this.filename = filename;
    JsonDataHelper.loadJson(filename, list, Schedule.class);
  }

  @Override
  public void insert(Schedule sch) {
    sch.setNo(Schedule.scheduleId++);
    if (sch != null) {
      this.list.add(sch);
    }
    JsonDataHelper.saveJson(filename, list);
  }

  @Override
  public List<Schedule> list() {
    return this.list;
  }

  @Override
  public Schedule findBy(int no) {
    Object[] list = this.list.toArray();
    for (int i = 0; i < this.list.size(); i++) {
      Schedule sch = (Schedule) list[i];
      if (sch.getNo() == no) {
        return sch;
      }
    }
    return null;
  }

  @Override
  public int update(Schedule sch) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getNo() == sch.getNo()) {
        list.set(i, sch);
        JsonDataHelper.saveJson(filename, list);
        return 1;
      }
    }
    return 0;
  }

  @Override
  public int remove(int no) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getNo() == no) {
        list.remove(i);
        JsonDataHelper.saveJson(filename, list);
        return 1;
      }
    }
    return 0;
  }
}
