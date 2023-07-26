package com.bitcamp.myapp.handler;

import java.io.IOException;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.DataSource;

public class ScheduleUpdateListener implements ScheduleActionListener {

  ScheduleDao scheduleDao;
  DataSource ds;

  public ScheduleUpdateListener(ScheduleDao scheduleDao, DataSource ds) {
    this.scheduleDao = scheduleDao;
    this.ds = ds;
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
        scheduleDao.update(newSch);

        try {
          ds.getConnection().commit();
        } catch (Exception e) {
          try {
            ds.getConnection().rollback();
          } catch (Exception e2) {
            // TODO: handle exception
          }
        }
      }
    }
  }

}
