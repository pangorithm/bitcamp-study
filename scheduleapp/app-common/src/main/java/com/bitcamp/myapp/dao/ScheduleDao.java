package com.bitcamp.myapp.dao;

import java.util.List;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;

public interface ScheduleDao {
  void insert(Schedule board);

  List<Schedule> findAllOwnedSchedule(Member loginUser);

  List<Schedule> findAllParticipatedSchedule(Member loginUser);

  List<Member> findAllParticipatedMember(int no);

  Schedule findBy(int no);

  int update(Schedule board);

  int delete(Schedule board);

  int addScheduleParticipant(int scheduleNo, int participantNo);

  int deleteScheduleParticipant(int scheduleNo, int memberNo);

  int deleteAllScheduleParticipant(int scheduleNo);

}
