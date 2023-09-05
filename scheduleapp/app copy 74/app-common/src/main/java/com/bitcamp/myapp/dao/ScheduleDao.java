package com.bitcamp.myapp.dao;

import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScheduleDao {

  int insert(Schedule schedule);

  List<Schedule> findAllOwnedSchedule(int memberNo);

  List<Schedule> findAllParticipatedSchedule(int memberNo);

  List<Member> findAllParticipatedMember(int no);

  Schedule findBy(int no);

  int update(Schedule schedule);

  int delete(int boardNo);

  int addScheduleParticipant(
      @Param("scheduleNo") int scheduleNo,
      @Param("participantNo") int participantNo);

  int deleteScheduleParticipant(
      @Param("scheduleNo") int scheduleNo,
      @Param("participantNo") int memberNo);

  int deleteAllScheduleParticipant(int scheduleNo);

}
