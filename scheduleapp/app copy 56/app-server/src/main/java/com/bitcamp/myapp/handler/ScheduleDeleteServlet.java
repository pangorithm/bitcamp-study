package com.bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;

@WebServlet("/schedule/delete")
public class ScheduleDeleteServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    Schedule sch = InitServlet.scheduleDao.findBy(Integer.parseInt(request.getParameter("no")));
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.printf("<meta http-equiv='refresh' content='1;url=/schedule/list'>\n");
    out.println("<title>스케줄</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>스케줄</h1>");

    if (sch == null) {
      out.println("<p>해당 번호의 스케줄이 없습니다!</p>");
    }

    if (!sch.getOwner().equals(loginUser)) {
      out.println("<p>삭제 권한이 없습니다.</p>");
      return;
    }

    try {
      InitServlet.scheduleDao.deleteAllScheduleParticipant(sch.getNo());
      InitServlet.scheduleDao.delete(sch);
      InitServlet.sqlSessionFactory.openSession(false).commit();
      out.println("<p>삭제 성공입니다</p>");
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      out.println("<p>삭제 실패입니다</p>");
      e.printStackTrace();
    }
    out.println("</body>");
    out.println("</html>");

  }

}
