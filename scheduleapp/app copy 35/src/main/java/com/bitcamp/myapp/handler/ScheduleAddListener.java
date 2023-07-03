package com.bitcamp.myapp.handler;

import java.util.List;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;

public class ScheduleAddListener extends AbstractScheduleListener {

  public ScheduleAddListener(List<Schedule> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Schedule sch = new Schedule();
    sch.setNo(Schedule.scheduleId++);
    sch = inputScheduleInfo(sch, prompt);
    if (sch != null) {
      this.list.add(sch);
    }
  }

}
