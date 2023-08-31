package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.ScheduleDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
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

    request.setAttribute("ownedList", scheduleDao.findAllOwnedSchedule(loginUser));
    request.setAttribute("participatedList", scheduleDao.findAllParticipatedSchedule(loginUser));
    return "/WEB-INF/jsp/schedule/list.jsp";
  }
}
