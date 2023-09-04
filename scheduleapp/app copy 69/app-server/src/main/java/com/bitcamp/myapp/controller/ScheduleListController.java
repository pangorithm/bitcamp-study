package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component("/schedule/list")
public class ScheduleListController implements PageController {

  ScheduleDao scheduleDao;

  public ScheduleListController(ScheduleDao scheduleDao) {
    this.scheduleDao = scheduleDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/login";
    }

    request.setAttribute("ownedList", scheduleDao.findAllOwnedSchedule(loginUser.getNo()));
    request.setAttribute("participatedList",
        scheduleDao.findAllParticipatedSchedule(loginUser.getNo()));
    return "/WEB-INF/jsp/schedule/list.jsp";
  }
}
