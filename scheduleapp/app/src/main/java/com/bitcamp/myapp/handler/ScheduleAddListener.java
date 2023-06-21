package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.List;

public class ScheduleAddListener extends AbstractScheduleListener {

  public ScheduleAddListener(List list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Schedule sch = new Schedule();
    this.list.add(inputScheduleInfo(sch, prompt));
  }

}
