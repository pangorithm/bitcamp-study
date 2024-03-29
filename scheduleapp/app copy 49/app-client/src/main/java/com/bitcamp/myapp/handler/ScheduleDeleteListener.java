package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.ClientApp;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;

public class ScheduleDeleteListener implements ScheduleActionListener {

  ScheduleDao scheduleDao;

  public ScheduleDeleteListener(ScheduleDao scheduleDao) {
    this.scheduleDao = scheduleDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Schedule sch = scheduleDao.findBy(prompt.inputInt("번호? "));
    if (sch == null) {
      System.out.println("해당 번호의 스케줄이 없습니다!");
      return;
    }
    if (!sch.getOwner().equals(ClientApp.loginUser)) {
      System.out.println("삭제 권한이 없습니다.");
      return;
    }
    ScheduleActionListener.printScheduleInfo(sch);
    if (prompt.inputString("정말로 이 스케줄을 삭제 하시겠습니까?(y/N)").equalsIgnoreCase("y")) {
      scheduleDao.remove(sch);
    }
  }

}
