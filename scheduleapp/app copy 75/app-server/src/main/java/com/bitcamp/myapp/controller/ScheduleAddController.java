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

@Controller("/schedule/add")
public class ScheduleAddController {

  @Autowired
  ScheduleService scheduleService;

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      return "/WEB-INF/jsp/schedule/form.jsp";
    }

    Schedule sch = new Schedule();
    sch.setTitle(
        request.getParameter("title"));
    sch.setContent(request
        .getParameter("content"));
    sch.setStartTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("start-time"))));
    sch.setEndTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("end-time"))));
    sch.setOwner((Member) request.getSession().getAttribute("loginUser"));

    try {
      scheduleService.add(sch);
      // 새로 생성된 스케줄 번호를 알아야함
      scheduleService.addScheduleParticipant(sch.getNo(),
          ((Member) request.getAttribute("loginUser")).getNo());
      return "redirect:list";
    } catch (Exception e) {

      request.setAttribute("message", "스케줄 등록 오류!");
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }
  }

}
