package com.bitcamp.myapp.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;

public class ScheduleAddListener implements ScheduleActionListener {

  ScheduleDao scheduleDao;
  SqlSessionFactory sqlSessionFactory;

  public ScheduleAddListener(ScheduleDao scheduleDao, SqlSessionFactory sqlSessionFactory) {
    this.scheduleDao = scheduleDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    Schedule sch = new Schedule();

    try {
      scheduleDao.insert(ScheduleActionListener.inputScheduleInfo(
          scheduleDao.findAllParticipatedSchedule((Member) prompt.getAttribute("loginUser")), sch,
          prompt));
      // 새로 생성된 스케줄 번호를 알아야함
      // scheduleDao.scheduleAddParticipant(0, ((Member) prompt.getAttribute("loginUser")).getNo());
      sqlSessionFactory.openSession(false).commit();
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
    }
  }

}
