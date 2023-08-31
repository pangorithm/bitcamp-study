package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.ScheduleDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import org.apache.ibatis.session.SqlSessionFactory;

@WebServlet("/schedule/delete")
public class ScheduleDeleteController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");
    ScheduleDao scheduleDao = (ScheduleDao) this.getServletContext().getAttribute("scheduleDao");

    Schedule sch = scheduleDao.findBy(Integer.parseInt(request.getParameter("no")));
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    try {
      if (sch == null) {
        throw new Exception("해당 번호의 스케줄이 없습니다!");
      }

      if (!sch.getOwner().equals(loginUser)) {
        throw new Exception("삭제 권한이 없습니다.");
      }

      scheduleDao.deleteAllScheduleParticipant(sch.getNo());
      scheduleDao.delete(sch);
      sqlSessionFactory.openSession(false).commit();
      request.setAttribute("viewUrl", "redirect:list");
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=list");
      request.setAttribute("exception", e);
    }

  }

}
