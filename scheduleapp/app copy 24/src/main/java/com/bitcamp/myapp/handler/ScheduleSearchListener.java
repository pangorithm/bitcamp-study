package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.List;

public class ScheduleSearchListener extends AbstractScheduleListener {

  public ScheduleSearchListener(List<Schedule> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    System.out.println("어떤 조건으로 검색하시겠습니까?");
    System.out.println("1. 스케줄 제목");
    System.out.println("2. 날짜 범위");
    String optNo = prompt.inputString("> ");

    if (optNo.equals("1")) {
      String searchTitle = prompt.inputString("제목 : ");
      for (Object obj : this.list.toArray()) {
        Schedule sch = (Schedule) obj;
        if (sch.getScheduleTitle().contains(searchTitle)) {
          printScheduleInfo(sch);
        }
      }
    } else if (optNo.equals("2")) {
      long searchRangeStart = parseDateLong(prompt.inputString("검색 시작 시간 ex)2023-06-05 16:30\n> "));
      long searchRangeEnd = parseDateLong(prompt.inputString("검색 종료 시간 ex)2023-06-06 20:00\n> "));
      for (Object obj : this.list.toArray()) {
        Schedule sch = (Schedule) obj;
        if (sch.getEndTime() > searchRangeStart && sch.getStartTime() < searchRangeEnd) {
          printScheduleInfo(sch);
        }
      }
    } else {
      System.out.println("올바르지 않은 형식입니다.");
    }
  }
}
