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

@WebServlet("/schedule/list")
public class ScheduleListServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

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
    out.println("<a href='/schedule/search.html'>스케줄 검색</a>\n");
    out.println("</div>");
    out.println("<table border='1'>");
    out.println("<thead>");
    out.println("<tr><th>번호</th> <th>제목</th> <th>시작</th> <th>종료</th> <th>스캐줄 매니저</th></tr>");
    out.println("</thead>");
    out.println("<tbody>");

    out.println("<tr><th><관리 스케줄></th></tr>");
    for (Object obj : InitServlet.scheduleDao.findAllOwnedSchedule(loginUser).toArray()) {
      Schedule sch = (Schedule) obj;
      out
          .printf(
              "<tr><td>%d</td> <td><a href='/schedule/detail?no=%d'>%s</a></td> <td>%s</td> <td>%s</td> <td>%s</td></tr>\n",
              sch.getNo(),
              sch.getNo(),
              sch.getScheduleTitle().matches("^\\s+$") ? "제목없음" : sch.getScheduleTitle(),
              sch.getStartTime().toString(),
              sch.getEndTime().toString(),
              sch.getOwner().getName());
    }

    out.println("<tr><th><참가 스케줄></th></tr>");
    for (Object obj : InitServlet.scheduleDao.findAllParticipatedSchedule(loginUser).toArray()) {
      Schedule sch = (Schedule) obj;
      out
          .printf(
              "<tr><td>%d</td> <td><a href='/schedule/detail?no=%d'>%s</a></td> <td>%s</td> <td>%s</td> <td>%s</td></tr>\n",
              sch.getNo(),
              sch.getNo(),
              sch.getScheduleTitle(),
              sch.getStartTime().toString(),
              sch.getEndTime().toString(),
              sch.getOwner().getName());
    }

    out.println("</tbody>");
    out.println("</table>");
    out.println("<a href='/'>메인</a>");
    out.println("</body>");
    out.println("</html>");
  }
}
