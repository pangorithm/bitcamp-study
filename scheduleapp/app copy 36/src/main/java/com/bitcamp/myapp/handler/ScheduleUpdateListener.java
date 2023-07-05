package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;

public class ScheduleUpdateListener implements ScheduleActionListener {

  ScheduleDao scheduleDao;

  public ScheduleUpdateListener(ScheduleDao scheduleDao) {
    this.scheduleDao = scheduleDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Schedule sch = scheduleDao.findBy(prompt.inputInt("번호? "));
    if (sch == null) {
      System.out.println("일치하는 번호가 존재하지 않습니다.");
    } else {
      System.out.println("수정 전--------------");
      ScheduleActionListener.printScheduleInfo(sch);
      System.out.println("---------------------");
      Schedule newSch = new Schedule(sch.getNo());
      newSch = ScheduleActionListener.inputScheduleInfo(scheduleDao.list(), newSch, prompt);
      if (newSch != null) {
        scheduleDao.update(newSch);
      }
    }
  }

}
