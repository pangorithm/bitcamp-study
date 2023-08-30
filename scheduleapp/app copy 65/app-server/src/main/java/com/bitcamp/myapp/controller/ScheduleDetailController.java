package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.ScheduleDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import org.apache.ibatis.session.SqlSessionFactory;

@WebServlet("/schedule/detail")
public class ScheduleDetailController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ScheduleDao scheduleDao = (ScheduleDao) this.getServletContext().getAttribute("scheduleDao");
    
    response.setContentType("text/html;charset=UTF-8");
    Schedule schedule = scheduleDao.findBy(Integer.parseInt((String) request.getParameter("no")));

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    if (schedule == null) {
      request.setAttribute("message", "해당 번호의 스케줄이 없습니다!");
      request.setAttribute("refresh", "2;url=list");

      request.getRequestDispatcher("../error.jsp").forward(request, response);
    } else {

      request.setAttribute("schedule", schedule);
      List<Member> participantList = scheduleDao.findAllParticipatedMember(schedule.getNo());
      request.setAttribute("participantList", participantList);
      HashSet<Member> memberSet = new HashSet<>();
      memberSet.add(schedule.getOwner());
      memberSet.addAll(participantList);

      if (memberSet.contains(loginUser)) {
        request.getRequestDispatcher("/WEB-INF/jsp/schedule/detail.jsp").include(request, response);
      } else {
        response.sendRedirect("list");
      }

    }

  }


}


