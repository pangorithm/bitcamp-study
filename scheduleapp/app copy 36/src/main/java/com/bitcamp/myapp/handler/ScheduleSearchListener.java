package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;

public class ScheduleSearchListener implements ScheduleActionListener {

  ScheduleDao scheduleDao;

  public ScheduleSearchListener(ScheduleDao scheduleDao) {
    this.scheduleDao = scheduleDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    System.out.println("어떤 조건으로 검색하시겠습니까?");
    System.out.println("1. 스케줄 제목");
    System.out.println("2. 날짜 범위");
    String optNo = prompt.inputString("> ");

    if (optNo.equals("1")) {
      String searchTitle = prompt.inputString("제목 : ");
      for (Object obj : scheduleDao.list().toArray()) {
        Schedule sch = (Schedule) obj;
        if (sch.getScheduleTitle().contains(searchTitle)) {
          ScheduleActionListener.printScheduleInfo(sch);
        }
      }
    } else if (optNo.equals("2")) {
      System.out.println("검색 시작 시간 ex)2023-06-05 16:30 ");
      long searchRangeStart = ScheduleActionListener.inputTime(prompt);
      System.out.println("검색 종료 시간 ex)2023-06-06 20:00 ");
      long searchRangeEnd = ScheduleActionListener.inputTime(prompt);
      ScheduleActionListener.searchSchedules(scheduleDao.list(), searchRangeStart, searchRangeEnd);
    } else {
      System.out.println("올바르지 않은 형식입니다.");
    }
  }


}
