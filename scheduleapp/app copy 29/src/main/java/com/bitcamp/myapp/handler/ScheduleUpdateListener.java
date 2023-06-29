package com.bitcamp.myapp.handler;

import java.util.List;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;

public class ScheduleUpdateListener extends AbstractScheduleListener {

  public ScheduleUpdateListener(List<Schedule> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Schedule sch = this.list.get(findBy(prompt.inputInt("번호? ")));
    if (sch == null) {
      System.out.println("일치하는 번호가 존재하지 않습니다.");
    } else {
      System.out.println("수정 전--------------");
      printScheduleInfo(sch);
      System.out.println("---------------------");
      inputScheduleInfo(sch, prompt);
    }
  }

}
