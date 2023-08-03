package com.bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.apache.ibatis.session.SqlSessionFactory;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.Component;
import com.bitcamp.util.HttpServletRequest;
import com.bitcamp.util.HttpServletResponse;
import com.bitcamp.util.Servlet;

@Component("/schedule/detail")
public class ScheduleDetailServlet implements Servlet {

  ScheduleDao scheduleDao;
  SqlSessionFactory sqlSessionFactory;

  public ScheduleDetailServlet(ScheduleDao scheduleDao, SqlSessionFactory sqlSessionFactory) {
    this.scheduleDao = scheduleDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Schedule schedule = scheduleDao.findBy(Integer.parseInt((String) request.getParameter("no")));

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    String addParticipantNo = (String) request.getParameter("addParticipantNo");
    String deleteParticipantNo = (String) request.getParameter("deleteParticipantNo");
    System.out.println(addParticipantNo + " " + deleteParticipantNo);
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>스케줄</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>스케줄</h1>");

    out.println("<p>");
    if (addParticipantNo != null) {
      try {
        int result =
            scheduleDao
                .addScheduleParticipant(schedule.getNo(), Integer.parseInt(addParticipantNo));

        if (result == -1) {
          out.println("이미 참가중인 멤버입니다.");
        } else if (result == -2) {
          out.println("존재하지 않는 멤버입니다.");
        } else {
          sqlSessionFactory.openSession(false).commit();
          out.println("참가자를 추가했습니다.");
        }

      } catch (Exception e) {
        sqlSessionFactory.openSession(false).rollback();
      }
    }

    if (deleteParticipantNo != null) {
      try {
        int result =
            scheduleDao
                .deleteScheduleParticipant(schedule.getNo(), Integer.parseInt(deleteParticipantNo));

        if (result == -1) {
          out.println("참여하지 않는 멤버입니다.");
        } else if (result == -2) {
          out.println("존재하지 않는 멤버입니다.");
        } else {
          sqlSessionFactory.openSession(false).commit();
          out.println("참가자를 삭제했습니다.");
        }

      } catch (Exception e) {
        sqlSessionFactory.openSession(false).rollback();
      }
    }
    out.println("</p>");

    if (schedule == null) {
      out.println("해당 번호의 스케줄이 없습니다!");
    } else {

      out.println("<form action='/schedule/update' method='post'>");
      out.println("<table border='1'>");
      out.printf(
        "<tr><th style='width:120px;'>번호</th> "
            + "<td style='width:300px;'><input type='text' name='no' value='%d' readonly='readonly'></td></tr>\n",
        schedule.getNo());
      out.printf(
        "<tr><th>제목</th><td><input type='text' name='title' value='%s'></td></tr>\n",
        schedule.getScheduleTitle());
      out.printf(
        "<tr><th>시작</th> <td><input type='datetime-local' name='start-time' value='%s'></td></tr>\n",
        schedule.getStartTime());
      out.printf(
        "<tr><th>종료</th> <td><input type='datetime-local' name='end-time' value='%s'></td></tr>\n",
        schedule.getEndTime());
      out.printf("<tr><th>스캐줄 매니저</th> <td>%s</td></tr>\n", schedule.getOwner().getName());
      out.println("</table>");
      out.println("<div>");
      out.println("<button>변경</button>");
      out.println("<button type='reset'>초기화</button>");
      out.printf("<a href='/schedule/delete?no=%d'>삭제</a>\n", schedule.getNo());
      out.printf("<a href='/schedule/list'>목록</a>\n");
      out.println("</div>");
      out.println("</form>");
      out.println("<div>");
      out.println("</div>");

      printParticipants(schedule, out);

      if (loginUser.getNo() == schedule.getOwner().getNo()) {

        out.println("<br>");
        out.println("<div>");
        out.println("참가자 번호");

        out.println("<form action='/schedule/detail' method='post'>");
        out.printf("<input type='hidden' name='no' value='%d'>", schedule.getNo());
        out.println("<input type='text' name='addParticipantNo'>");
        out.println("<button>추가</button>");
        out.println("</form>");

        out.println("<form action='/schedule/detail' method='post'>");
        out.printf("<input type='hidden' name='no' value='%d'>", schedule.getNo());
        out.println("<input type='text' name='deleteParticipantNo'>");
        out.println("<button>삭제</button>");
        out.println("</form>");

        out.println("</div>");
      }

    }
    out.println("</body>");
    out.println("</html>");
  }

  private void printParticipants(Schedule schedule, PrintWriter out) throws IOException {

    List<Member> participantList = scheduleDao.findAllParticipatedMember(schedule.getNo());
    out.println("<br>");
    out.println("<p1>스케줄 참가자 명단</p1><br>");

    out.println("<table border='1'>");
    out.println("<tr><th style='width:100px;'>번호</th> <th style='width:100px;'>이름</th>");

    for (Member m : participantList.toArray(new Member[0])) {
      out.printf(
        "<tr><td>%d</td> <td><a href='/member/detail?no=%d'>%s</a></td></tr>\n",
        m.getNo(),
        m.getNo(),
        m.getName());
    }
    out.println("</table>");
  }
}


