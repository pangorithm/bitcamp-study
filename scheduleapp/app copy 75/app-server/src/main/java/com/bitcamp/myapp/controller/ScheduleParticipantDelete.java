package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.ScheduleService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("/schedule/participantDelete")
public class ScheduleParticipantDelete {

  @Autowired
  ScheduleService scheduleService;

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int scheduleNo = Integer.parseInt(request.getParameter("no"));
    int deleteParticipantNo = Integer.parseInt(request.getParameter("deleteParticipantNo"));

    try {
      int result = scheduleService.deleteScheduleParticipant(scheduleNo, deleteParticipantNo);

      if (result == -1) {
        throw new Exception("참여하지 않는 멤버입니다.");
      } else if (result == -2) {
        throw new Exception("존재하지 않는 멤버입니다.");
      } else {
        return "redirect:detail?no=" + scheduleNo;
      }

    } catch (Exception e) {
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=detail?no=" + scheduleNo);
      throw e;
    }
  }

}
