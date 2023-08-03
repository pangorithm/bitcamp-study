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

@Component("/schedule/update")
public class ScheduleUpdateServlet implements Servlet {

  ScheduleDao scheduleDao;
  SqlSessionFactory sqlSessionFactory;

  public ScheduleUpdateServlet(ScheduleDao scheduleDao, SqlSessionFactory sqlSessionFactory) {
    this.scheduleDao = scheduleDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Schedule sch = new Schedule();
    sch.setNo(Integer.parseInt(request.getParameter("no")));
    sch.setScheduleTitle(request.getParameter("title"));
    sch.setStartTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("start-time"))));
    sch.setEndTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("end-time"))));
    sch.setOwner((Member) request.getSession().getAttribute("loginUser"));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.printf("<meta http-equiv='refresh' content='1;url=/schedule/list'>\n");
    out.println("<title>스케줄</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>스케줄 변경</h1>");

    try {
      if (scheduleDao.update(sch) == 0) {
        out.println("스케줄이 없거나 변경 권한이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        out.println("변경했습니다!");
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      out.println("스케줄 변경 실패입니다!");
      e.printStackTrace();
    }
    out.println("</body>");
    out.println("</html>");
  }
}


