package com.bitcamp.myapp.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;

public class ScheduleUpdateListener implements ScheduleActionListener {

  ScheduleDao scheduleDao;
  SqlSessionFactory sqlSessionFactory;

  public ScheduleUpdateListener(ScheduleDao scheduleDao, SqlSessionFactory sqlSessionFactory) {
    this.scheduleDao = scheduleDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    Schedule sch =
        scheduleDao.findBy(prompt.inputInt("번호? "), (Member) prompt.getAttribute("loginUser"));
    if (sch == null) {
      prompt.println("일치하는 번호가 존재하지 않습니다.");
    } else {
      if (!sch.getOwner().equals(prompt.getAttribute("loginUser"))) {
        prompt.println("수정 권한이 없습니다!");
        return;
      }
      prompt.println("수정 전--------------");
      ScheduleActionListener.printScheduleInfo(sch, prompt);
      prompt.println("---------------------");
      Schedule newSch = new Schedule();
      newSch.setNo(sch.getNo());
      newSch = ScheduleActionListener.inputScheduleInfo(
          scheduleDao.list((Member) prompt.getAttribute("loginUser")), newSch, prompt);
      if (newSch != null) {

        try {
          scheduleDao.update(newSch);
          sqlSessionFactory.openSession(false).commit();
        } catch (Exception e) {
          sqlSessionFactory.openSession(false).rollback();
        }
      }
    }
  }

}
