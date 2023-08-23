package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.util.NcpObjectStorageService;
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
import org.apache.ibatis.session.SqlSessionFactory;

@WebServlet("/schedule/update")
public class ScheduleUpdateServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");
    ScheduleDao scheduleDao = (ScheduleDao) this.getServletContext().getAttribute("scheduleDao");

    Schedule sch = new Schedule();
    sch.setNo(Integer.parseInt(request.getParameter("no")));
    sch
        .setTitle(
            request.getParameter("title").replaceAll("<script", "<scr!pt").replaceAll("<a", "<@"));
    sch
        .setContent(
            request
                .getParameter("content")
                .replaceAll("<script", "<scr!pt")
                .replaceAll("<a", "<@"));
    sch.setStartTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("start-time"))));
    sch.setEndTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("end-time"))));
    sch.setOwner((Member) request.getSession().getAttribute("loginUser"));

    try {
      if (scheduleDao.update(sch) == 0) {
        throw new Exception("스케줄이 없거나 변경 권한이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list");
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=detail?no=" + sch.getNo());

      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}


