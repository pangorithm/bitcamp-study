package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.List;

public class ScheduleListListener extends AbstractScheduleListener {

  public ScheduleListListener(List list) {
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
