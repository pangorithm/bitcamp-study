package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.util.Component;
import com.bitcamp.util.HttpServletRequest;
import com.bitcamp.util.HttpServletResponse;
import com.bitcamp.util.Servlet;

@Component("/schedule/search")
public class ScheduleSearchServlet implements Servlet {

  ScheduleDao scheduleDao;

  public ScheduleSearchServlet(ScheduleDao scheduleDao) {
    this.scheduleDao = scheduleDao;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
    // String optNo = prompt.inputString("어떤 조건으로 검색하시겠습니까?\n 1. 스케줄 제목\n 2. 날짜 범위\n > ");
    //
    // if (optNo.equals("1")) {
    // String searchTitle = prompt.inputString("제목 : ");
    // for (Object obj : scheduleDao
    // .findAllParticipatedSchedule((Member) prompt.getAttribute("loginUser"))
    // .toArray()) {
    // Schedule sch = (Schedule) obj;
    // if (sch.getScheduleTitle().contains(searchTitle)) {
    // ScheduleActionListener.printScheduleInfo(sch, prompt);
    // }
    // }
    // } else if (optNo.equals("2")) {
    // long searchRangeStart =
    // ScheduleActionListener.inputTime("검색 시작 시간\n ex)2023-06-05 16:30\n ", prompt).getTime();
    // long searchRangeEnd =
    // ScheduleActionListener.inputTime("검색 종료 시간\n ex)2023-06-06 20:00\n ", prompt).getTime();
    // for (Schedule sch : ScheduleActionListener.searchSchedules(
    // scheduleDao.findAllParticipatedSchedule((Member) prompt.getAttribute("loginUser")),
    // searchRangeStart,
    // searchRangeEnd)) {
    // ScheduleActionListener.printScheduleInfo(sch, prompt);
    // }
    // } else {
    // prompt.println("올바르지 않은 형식입니다.");
    // }
  }


}
