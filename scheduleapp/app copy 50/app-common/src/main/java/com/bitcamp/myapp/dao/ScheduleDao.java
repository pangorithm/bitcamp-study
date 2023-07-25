package com.bitcamp.myapp.dao;

import java.util.List;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;

public interface ScheduleDao {
  void insert(Schedule board);

  List<Schedule> list(Member loginUser);

  Schedule findBy(int no, Member loginUser);

  int update(Schedule board);

  int remove(Schedule board);

  int scheduleAddParticipant(int scheduleNo, int participantNo);
}
