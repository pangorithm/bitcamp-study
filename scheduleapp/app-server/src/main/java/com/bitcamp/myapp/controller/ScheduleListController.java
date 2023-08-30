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

@WebServlet("/schedule/list")
public class ScheduleListController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ScheduleDao scheduleDao = (ScheduleDao) this.getServletContext().getAttribute("scheduleDao");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/login");
      return;
    }

    request.setAttribute("ownedList", scheduleDao.findAllOwnedSchedule(loginUser));
    request.setAttribute("participatedList", scheduleDao.findAllParticipatedSchedule(loginUser));

    request.getRequestDispatcher("/WEB-INF/jsp/schedule/list.jsp").include(request, response);
  }
}
