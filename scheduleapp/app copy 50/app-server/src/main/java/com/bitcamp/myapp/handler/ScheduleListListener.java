package com.bitcamp.myapp.handler;

import java.io.IOException;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;

public class ScheduleListListener implements ScheduleActionListener {

  ScheduleDao scheduleDao;

  public ScheduleListListener(ScheduleDao scheduleDao) {
    this.scheduleDao = scheduleDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    for (Object obj : scheduleDao.list((Member) prompt.getAttribute("loginUser")).toArray()) {
      Schedule sch = (Schedule) obj;
      ScheduleActionListener.printScheduleInfo(sch, prompt);
    }
  }
}
