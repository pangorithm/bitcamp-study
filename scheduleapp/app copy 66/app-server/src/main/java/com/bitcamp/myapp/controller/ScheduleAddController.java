package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.ScheduleDao;
import java.io.IOException;
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

@WebServlet("/schedule/add")
public class ScheduleAddController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setAttribute("viewUrl", "/WEB-INF/jsp/schedule/form.jsp");
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");
    ScheduleDao scheduleDao = (ScheduleDao) this.getServletContext().getAttribute("scheduleDao");

    Schedule sch = new Schedule();
    sch.setTitle(
        request.getParameter("title"));
    sch.setContent(request
        .getParameter("content"));
    sch.setStartTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("start-time"))));
    sch.setEndTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("end-time"))));
    sch.setOwner((Member) request.getSession().getAttribute("loginUser"));

    try {
      scheduleDao.insert(sch);
      // 새로 생성된 스케줄 번호를 알아야함
      // scheduleDao.scheduleAddParticipant(0, ((Member) prompt.getAttribute("loginUser")).getNo());
      sqlSessionFactory.openSession(false).commit();
      request.setAttribute("viewUrl", "redirect:list");
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();

      request.setAttribute("message", "스케줄 등록 오류!");
      request.setAttribute("refresh", "2;url=list");
      request.setAttribute("exception", e);
    }
  }

}
