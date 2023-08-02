package com.bitcamp.myapp.handler;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.apache.ibatis.session.SqlSessionFactory;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.Component;
import com.bitcamp.util.HttpServletRequest;
import com.bitcamp.util.HttpServletResponse;
import com.bitcamp.util.Servlet;

@Component("/schedule/add")
public class ScheduleAddServlet implements Servlet {

  ScheduleDao scheduleDao;
  SqlSessionFactory sqlSessionFactory;

  public ScheduleAddServlet(ScheduleDao scheduleDao, SqlSessionFactory sqlSessionFactory) {
    this.scheduleDao = scheduleDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Schedule sch = new Schedule();
    sch.setScheduleTitle(request.getParameter("title"));
    sch.setStartTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("start-time"))));
    sch.setEndTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("end-time"))));
    sch.setOwner((Member) request.getAttribute("loginUser"));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
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
      scheduleDao.insert(sch);
      // 새로 생성된 스케줄 번호를 알아야함
      // scheduleDao.scheduleAddParticipant(0, ((Member) prompt.getAttribute("loginUser")).getNo());
      sqlSessionFactory.openSession(false).commit();
      out.println("<p>등록 성공입니다</p>");
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      out.println("<p>등록 실패입니다</p>");
      e.printStackTrace();
    }
    out.println("</body>");
    out.println("</html>");
  }

}
