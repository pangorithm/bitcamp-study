package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.ScheduleService;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import java.util.HashSet;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("/schedule/detail")
public class ScheduleDetailController implements PageController {

  @Autowired
  ScheduleService scheduleService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Schedule schedule = scheduleService.get(Integer.parseInt((String) request.getParameter("no")));

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    if (schedule == null) {
      request.setAttribute("refresh", "2;url=list");
      throw new Exception("해당 번호의 스케줄이 없습니다!");
    } else {

      request.setAttribute("schedule", schedule);
      List<Member> participantList = scheduleService.getParticipatedMemberList(schedule.getNo());
      request.setAttribute("participantList", participantList);
      HashSet<Member> memberSet = new HashSet<>();
      memberSet.add(schedule.getOwner());
      memberSet.addAll(participantList);

      if (memberSet.contains(loginUser)) {
        return "/WEB-INF/jsp/schedule/detail.jsp";
      } else {
        return "redirect:list";
      }

    }

  }


}


