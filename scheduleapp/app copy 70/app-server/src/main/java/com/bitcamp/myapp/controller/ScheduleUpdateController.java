package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.ScheduleService;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("/schedule/update")
public class ScheduleUpdateController implements PageController {

  @Autowired
  ScheduleService scheduleService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Schedule sch = scheduleService.get(Integer.parseInt(request.getParameter("no")));
    if (sch == null
        || sch.getOwner().getNo()
        != ((Member) request.getSession().getAttribute("loginUser")).getNo()) {
      throw new Exception("스케줄이 없거나 변경 권한이 없습니다.");
    }

    sch.setTitle(request.getParameter("title"));
    sch.setContent(request.getParameter("content"));
    sch.setStartTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("start-time"))));
    sch.setEndTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("end-time"))));

    try {
      scheduleService.update(sch);
      return "redirect:list";
    } catch (Exception e) {
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=detail?no=" + sch.getNo());
      throw e;
    }
  }
}


