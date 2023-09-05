package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.ScheduleService;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("/schedule/delete")
public class ScheduleDeleteController implements PageController {

  @Autowired
  ScheduleService scheduleService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Schedule sch = scheduleService.get(Integer.parseInt(request.getParameter("no")));
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    try {
      if (sch == null) {
        throw new Exception("해당 번호의 스케줄이 없습니다!");
      }

      if (!sch.getOwner().equals(loginUser)) {
        throw new Exception("삭제 권한이 없습니다.");
      }

      scheduleService.delete(sch.getNo());
      return "redirect:list";
    } catch (Exception e) {
      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }

  }

}
