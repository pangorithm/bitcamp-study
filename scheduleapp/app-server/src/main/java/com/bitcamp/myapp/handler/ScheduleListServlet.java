package com.bitcamp.myapp.handler;

import java.io.PrintWriter;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.Component;
import com.bitcamp.util.HttpServletRequest;
import com.bitcamp.util.HttpServletResponse;
import com.bitcamp.util.Servlet;

@Component("/schedule/list")
public class ScheduleListServlet implements Servlet {

  ScheduleDao scheduleDao;

  public ScheduleListServlet(ScheduleDao scheduleDao) {
    this.scheduleDao = scheduleDao;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>스케줄</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>스케줄 목록 </h1>");
    out.println("<div style='margin:5px;'>");
    out.println("<a href='/schedule/form.html'>새 스케줄</a>\n");
    out.println("</div>");
    out.println("<table border='1'>");
    out.println("<thead>");
    out.println("<tr><th>번호</th> <th>제목</th> <th>시작</th> <th>종료</th></tr>");
    out.println("</thead>");
    out.println("<tbody>");

    out.println("<tr><th><소유한 스케줄></th></tr>");
    for (Object obj : scheduleDao.findAllOwnedSchedule((Member) request.getAttribute("loginUser"))
        .toArray()) {
      Schedule sch = (Schedule) obj;
      out.printf(
          "<tr><td>%d</td> <td><a href='/schedule/detail?no=%d'>%s</a></td> <td>%s</td> <td>%s</td></tr>\n",
          sch.getNo(),
          sch.getNo(),
          sch.getScheduleTitle(),
          sch.getStartTime().toString(),
          sch.getEndTime().toString());
    }

    out.println("<tr><th><참가한 스케줄></th></tr>");
    for (Object obj : scheduleDao
        .findAllParticipatedSchedule((Member) request.getAttribute("loginUser"))
        .toArray()) {
      Schedule sch = (Schedule) obj;
      out.printf(
          "<tr><td>%d</td> <td><a href='/schedule/detail?no=%d'>%s</a></td> <td>%s</td> <td>%s</td></tr>\n",
          sch.getNo(),
          sch.getNo(),
          sch.getScheduleTitle(),
          sch.getStartTime().toString(),
          sch.getEndTime().toString());
    }
  }
}