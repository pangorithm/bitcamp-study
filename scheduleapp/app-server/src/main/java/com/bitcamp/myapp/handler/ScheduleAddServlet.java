package com.bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;

@WebServlet("/schedule/add")
public class ScheduleAddServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    Schedule sch = new Schedule();
    sch.setScheduleTitle(request.getParameter("title").replaceAll("<script", "<s c r i p t"));
    sch.setStartTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("start-time"))));
    sch.setEndTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("end-time"))));
    sch.setOwner((Member) request.getSession().getAttribute("loginUser"));

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='1;url=/schedule/list'>");
    out.println("<title>스케줄</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>스케줄 등록</h1>");
    try {
      InitServlet.scheduleDao.insert(sch);
      // 새로 생성된 스케줄 번호를 알아야함
      // scheduleDao.scheduleAddParticipant(0, ((Member) prompt.getAttribute("loginUser")).getNo());
      InitServlet.sqlSessionFactory.openSession(false).commit();
      out.println("<p>등록 성공입니다</p>");
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      out.println("<p>등록 실패입니다</p>");
      e.printStackTrace();
    }
    out.println("</body>");
    out.println("</html>");
  }

}
