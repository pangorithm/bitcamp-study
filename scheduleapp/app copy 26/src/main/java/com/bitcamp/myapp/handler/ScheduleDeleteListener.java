package com.bitcamp.myapp.handler;

import java.util.List;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;

public class ScheduleDeleteListener extends AbstractScheduleListener {

  public ScheduleDeleteListener(List<Schedule> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Schedule sch = this.list.get(findBy(prompt.inputInt("번호? ")));
    if (sch == null) {
      System.out.println("해당 번호의 스케줄이 없습니다!");
      return;
    }
    printScheduleInfo(sch);
    if (prompt.inputString("정말로 이 스케줄을 삭제 하시겠습니까?(y/N)").equalsIgnoreCase("y")) {
      list.remove(sch);
    }
  }

}
