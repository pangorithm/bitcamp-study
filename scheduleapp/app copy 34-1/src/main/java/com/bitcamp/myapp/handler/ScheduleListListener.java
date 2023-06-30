package com.bitcamp.myapp.handler;

import java.util.List;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;

public class ScheduleListListener extends AbstractScheduleListener {

  public ScheduleListListener(List<Schedule> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    for (Object obj : this.list.toArray()) {
      Schedule sch = (Schedule) obj;
      printScheduleInfo(sch);
    }
  }
}
