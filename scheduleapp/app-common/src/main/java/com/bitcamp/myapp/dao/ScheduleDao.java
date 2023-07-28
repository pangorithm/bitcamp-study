package com.bitcamp.myapp.dao;

import java.util.List;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;

public interface ScheduleDao {
  void insert(Schedule board);

  List<Schedule> findAllOwnedSchedule(Member loginUser);

  List<Schedule> findAllParticipatedSchedule(Member loginUser);

  List<Member> findAllParticipatedMember(int no);

  Schedule findBy(int no, Member loginUser);

  int update(Schedule board);

  int delete(Schedule board);

  int scheduleAddParticipant(int scheduleNo, int participantNo);

  int scheduleDeleteParticipant(int scheduleNo, int memberNo);

}
