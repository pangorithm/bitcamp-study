package com.bitcamp.myapp.dao;

import java.util.List;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;

public interface ScheduleDao {
  void insert(Schedule board);

  List<Schedule> list(Member loginUser);

  List<Member> participantList(int no);

  Schedule findBy(int no, Member loginUser);

  int update(Schedule board);

  int delete(Schedule board);

  int scheduleAddParticipant(int scheduleNo, int participantNo);

}
