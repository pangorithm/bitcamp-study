package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;

public class ScheduleListListener implements ScheduleActionListener {

  ScheduleDao scheduleDao;

  public ScheduleListListener(ScheduleDao scheduleDao) {
    this.scheduleDao = scheduleDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    for (Object obj : scheduleDao.list().toArray()) {
      Schedule sch = (Schedule) obj;
      ScheduleActionListener.printScheduleInfo(sch);
    }
  }
}
