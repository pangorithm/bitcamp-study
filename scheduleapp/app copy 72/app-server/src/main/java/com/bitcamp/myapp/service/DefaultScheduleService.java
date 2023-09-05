package com.bitcamp.myapp.service;

import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class DefaultScheduleService implements ScheduleService {

  ScheduleDao scheduleDao;
  TransactionTemplate txTemplate;

  public DefaultScheduleService(ScheduleDao scheduleDao, PlatformTransactionManager txManager) {
    this.scheduleDao = scheduleDao;
    this.txTemplate = new TransactionTemplate(txManager);
  }

  @Override
  public int add(Schedule schedule) throws Exception {
    return txTemplate.execute(status -> scheduleDao.insert(schedule));
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

  @Override
  public int update(Schedule schedule) throws Exception {
    return txTemplate.execute(status -> scheduleDao.update(schedule));
  }

  @Override
  public int delete(int scheduleNo) throws Exception {
    return txTemplate.execute(status -> {
      scheduleDao.deleteAllScheduleParticipant(scheduleNo);
      return scheduleDao.delete(scheduleNo);
    });
  }

  @Override
  public int addScheduleParticipant(int scheduleNo, int memberNo) throws Exception {
    return txTemplate.execute(status -> scheduleDao.addScheduleParticipant(scheduleNo, memberNo));
  }

  @Override
  public int deleteScheduleParticipant(int scheduleNo, int memberNo) throws Exception {
    return txTemplate.execute(status ->
        scheduleDao.deleteScheduleParticipant(scheduleNo, memberNo));
  }

}
