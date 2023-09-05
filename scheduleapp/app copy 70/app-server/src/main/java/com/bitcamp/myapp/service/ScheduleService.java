package com.bitcamp.myapp.service;

import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import java.util.List;
import java.util.Map;

// 비즈니스 로직을 수행하는 객체의 사용 규칙 정의
// 메서드 이름은 업무와 관련된 이륾을 사용할 것.
//
public interface ScheduleService {

  int add(Schedule schedule) throws Exception;

  Map<String, List<Schedule>> listMap(int memberNo) throws Exception;

  Schedule get(int scheduleNo) throws Exception;

  List<Member> getParticipatedMemberList(int scheduleNo);

  int update(Schedule schedule) throws Exception;

  int delete(int scheduleNo) throws Exception;

  int addScheduleParticipant(int scheduleNo, int memberNo);

  int deleteScheduleParticipant(int scheduleNo, int memberNo);
}
