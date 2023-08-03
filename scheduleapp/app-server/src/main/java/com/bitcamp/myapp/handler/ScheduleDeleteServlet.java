package com.bitcamp.myapp.handler;

import java.io.PrintWriter;
import org.apache.ibatis.session.SqlSessionFactory;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.Component;
import com.bitcamp.util.HttpServletRequest;
import com.bitcamp.util.HttpServletResponse;
import com.bitcamp.util.Servlet;

@Component("/schedule/delete")
public class ScheduleDeleteServlet implements Servlet {

  ScheduleDao scheduleDao;
  SqlSessionFactory sqlSessionFactory;

  public ScheduleDeleteServlet(ScheduleDao scheduleDao, SqlSessionFactory sqlSessionFactory) {
    this.scheduleDao = scheduleDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Schedule sch = scheduleDao.findBy(Integer.parseInt(request.getParameter("no")));

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
    out.println("<h1>스케줄</h1>");

    if (sch == null) {
      out.println("<p>해당 번호의 스케줄이 없습니다!</p>");
    }

    sch.setOwner(((Member) request.getSession().getAttribute("loginUser")));

    if (!sch.getOwner().equals(request.getSession().getAttribute("loginUser"))) {
      out.println("<p>삭제 권한이 없습니다.</p>");
      return;
    }

    try {
      scheduleDao.delete(sch);
      sqlSessionFactory.openSession(false).commit();
      out.println("<p>삭제 성공입니다</p>");
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      out.println("<p>삭제 실패입니다</p>");
      // 해당 스케줄에 참가자가 남아있을 경우 fk 규칙에 의해 삭제 불가
      e.printStackTrace();
    }
    out.println("</body>");
    out.println("</html>");

  }

}
