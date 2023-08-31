package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Schedule;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSessionFactory;

@WebServlet("/schedule/participantAdd")
public class ScheduleParticipantAdd extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");
    ScheduleDao scheduleDao = (ScheduleDao) this.getServletContext().getAttribute("scheduleDao");
    int scheduleNo = Integer.parseInt(request.getParameter("no"));
    int addParticipantNo = Integer.parseInt(request.getParameter("addParticipantNo"));
    try {
      int result = scheduleDao.addScheduleParticipant(scheduleNo, addParticipantNo);

      if (result == -1) {
        throw new Exception("이미 참가중인 멤버입니다.");
      } else if (result == -2) {
        throw new Exception("존재하지 않는 멤버입니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        request.setAttribute("viewUrl", "redirect:detail?no=" + scheduleNo);
      }

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=detail?no=" + scheduleNo);
      request.setAttribute("exception", e);
    }
  }

}
