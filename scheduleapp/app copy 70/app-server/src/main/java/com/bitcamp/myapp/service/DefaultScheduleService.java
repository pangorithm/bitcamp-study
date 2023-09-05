package com.bitcamp.myapp.service;

import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class DefaultScheduleService implements ScheduleService {

  ScheduleDao scheduleDao;
  PlatformTransactionManager txManager;

  public DefaultScheduleService(ScheduleDao scheduleDao, PlatformTransactionManager txManager) {
    this.scheduleDao = scheduleDao;
    this.txManager = txManager;
  }

  @Override
  public int add(Schedule schedule) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      int count = scheduleDao.insert(schedule);
      txManager.commit(status);
      return count;
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
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
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      int count = scheduleDao.update(schedule);
      txManager.commit(status);
      return count;
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }

  @Override
  public int delete(int scheduleNo) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      scheduleDao.deleteAllScheduleParticipant(scheduleNo);
      int count = scheduleDao.delete(scheduleNo);
      txManager.commit(status);
      return count;
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }

  @Override
  public int addScheduleParticipant(int scheduleNo, int memberNo) {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      int count = scheduleDao.addScheduleParticipant(scheduleNo, memberNo);
      txManager.commit(status);
      return count;
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }

  @Override
  public int deleteScheduleParticipant(int scheduleNo, int memberNo) {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      int count = scheduleDao.deleteScheduleParticipant(scheduleNo, memberNo);
      txManager.commit(status);
      return count;
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }

}
