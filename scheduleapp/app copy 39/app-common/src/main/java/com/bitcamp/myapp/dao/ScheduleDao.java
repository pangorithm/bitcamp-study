package com.bitcamp.myapp.dao;

import java.util.List;
import com.bitcamp.myapp.vo.Schedule;

public interface ScheduleDao {
  void insert(Schedule board);

  List<Schedule> list();

  Schedule findBy(int no);

  int update(Schedule board);

  int remove(int no);
}
