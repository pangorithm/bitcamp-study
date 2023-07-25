package com.bitcamp.myapp.handler;

import java.io.IOException;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;

public class ScheduleAddListener implements ScheduleActionListener {

  ScheduleDao scheduleDao;

  public ScheduleAddListener(ScheduleDao scheduleDao) {
    this.scheduleDao = scheduleDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    Schedule sch = new Schedule();
    scheduleDao.insert(ScheduleActionListener.inputScheduleInfo(
        scheduleDao.list((Member) prompt.getAttribute("loginUser")), sch, prompt));
  }

}
