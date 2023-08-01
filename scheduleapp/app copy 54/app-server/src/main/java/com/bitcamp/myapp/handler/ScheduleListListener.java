package com.bitcamp.myapp.handler;

import java.io.IOException;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.Component;

@Component("/schedule/list")
public class ScheduleListListener implements ScheduleActionListener {

  ScheduleDao scheduleDao;

  public ScheduleListListener(ScheduleDao scheduleDao) {
    this.scheduleDao = scheduleDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    prompt.println("<소유한 스케줄>");
    for (Object obj : scheduleDao.findAllOwnedSchedule((Member) prompt.getAttribute("loginUser"))
        .toArray()) {
      Schedule sch = (Schedule) obj;
      ScheduleActionListener.printScheduleInfo(sch, prompt);
    }

    prompt.println("<참가한 스케줄>");
    for (Object obj : scheduleDao
        .findAllParticipatedSchedule((Member) prompt.getAttribute("loginUser")).toArray()) {
      Schedule sch = (Schedule) obj;
      ScheduleActionListener.printScheduleInfo(sch, prompt);
    }
  }
}
