package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.ScheduleDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;

@WebServlet("/schedule/search")
public class ScheduleSearchController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setAttribute("viewUrl", "/WEB-INF/jsp/schedule/searchForm.jsp");
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ScheduleDao scheduleDao = (ScheduleDao) this.getServletContext().getAttribute("scheduleDao");
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    String searchTitle = request.getParameter("title");
    long searchRangeStart =
        localDateTimeToLong(LocalDateTime.parse(request.getParameter("start-time")));
    long searchRangeEnd =
        localDateTimeToLong(LocalDateTime.parse(request.getParameter("end-time")));

    HashSet<Schedule> set = new HashSet<>(scheduleDao.findAllParticipatedSchedule(loginUser));
    set.addAll(scheduleDao.findAllOwnedSchedule(loginUser));
    set.removeIf(sch ->
        !(searchTitle.length() > 0 && sch.getTitle().matches(String.format(".*%s.*", searchTitle))
            || (sch.getEndTime().getTime() > searchRangeStart
            && sch.getStartTime().getTime() < searchRangeEnd))
    );
    request.setAttribute("list", new ArrayList<Schedule>(set));
    request.setAttribute("viewUrl", "/WEB-INF/jsp/schedule/search.jsp");
  }

  private long localDateTimeToLong(LocalDateTime localDateTime) {
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }
}
