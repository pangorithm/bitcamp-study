package com.bitcamp.myapp.service;

import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultScheduleService implements ScheduleService {

  ScheduleDao scheduleDao;

  public DefaultScheduleService(ScheduleDao scheduleDao) {
    this.scheduleDao = scheduleDao;
  }

  @Transactional
  @Override
  public int add(Schedule schedule) throws Exception {
    return scheduleDao.insert(schedule);
  }

  @Override
  public Map<String, List<Schedule>> listMap(int memberNo) throws Exception {
    Map<String, List<Schedule>> map = new HashMap<>();
    map.put("ownedList", scheduleDao.findAllOwnedSchedule(memberNo));
    map.put("participatedList", scheduleDao.findAllParticipatedSchedule(memberNo));
    return map;
  }

  @Override
  public Schedule get(int scheduleNo) throws Exception {
    Schedule schedule = scheduleDao.findBy(scheduleNo);
    schedule.setParticipantList(scheduleDao.findAllParticipatedMember(schedule.getNo()));
    return schedule;
  }

  @Override
  public List<Member> getParticipatedMemberList(int scheduleNo) {
    return scheduleDao.findAllParticipatedMember(scheduleNo);
  }

  @Transactional
  @Override
  public int update(Schedule schedule) throws Exception {
    return scheduleDao.update(schedule);
  }

  @Transactional
  @Override
  public int delete(int scheduleNo) throws Exception {
    scheduleDao.deleteAllScheduleParticipant(scheduleNo);
    return scheduleDao.delete(scheduleNo);
  }

  @Transactional
  @Override
  public int addScheduleParticipant(int scheduleNo, int memberNo) throws Exception {
    return scheduleDao.addScheduleParticipant(scheduleNo, memberNo);
  }

  @Transactional
  @Override
  public int deleteScheduleParticipant(int scheduleNo, int memberNo) throws Exception {
    return scheduleDao.deleteScheduleParticipant(scheduleNo, memberNo);
  }

}
