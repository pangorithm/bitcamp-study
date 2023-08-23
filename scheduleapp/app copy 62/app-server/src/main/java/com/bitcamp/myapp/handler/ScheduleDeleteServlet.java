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

    Schedule sch = InitServlet.scheduleDao.findBy(Integer.parseInt(request.getParameter("no")));
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    try {
      if (sch == null) {
        throw new Exception("해당 번호의 스케줄이 없습니다!");
      }

      if (!sch.getOwner().equals(loginUser)) {
        throw new Exception("삭제 권한이 없습니다.");
      }

      InitServlet.scheduleDao.deleteAllScheduleParticipant(sch.getNo());
      InitServlet.scheduleDao.delete(sch);
      InitServlet.sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("/schedule/list");
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=list");

      request.getRequestDispatcher("/error").forward(request, response);
    }

  }

}
