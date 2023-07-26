package com.bitcamp.myapp.handler;

import java.io.IOException;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.DataSource;

public class ScheduleAddListener implements ScheduleActionListener {

  ScheduleDao scheduleDao;
  DataSource ds;

  public ScheduleAddListener(ScheduleDao scheduleDao, DataSource ds) {
    this.scheduleDao = scheduleDao;
    this.ds = ds;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    Schedule sch = new Schedule();

    try {
      scheduleDao.insert(ScheduleActionListener.inputScheduleInfo(
          scheduleDao.list((Member) prompt.getAttribute("loginUser")), sch, prompt));
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
