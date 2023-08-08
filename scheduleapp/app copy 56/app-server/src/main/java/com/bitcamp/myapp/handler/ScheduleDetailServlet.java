package com.bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;

@WebServlet("/schedule/detail")
public class ScheduleDetailServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    Schedule schedule =
        InitServlet.scheduleDao.findBy(Integer.parseInt((String) request.getParameter("no")));

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    String addParticipantNo = (String) request.getParameter("addParticipantNo");
    String deleteParticipantNo = (String) request.getParameter("deleteParticipantNo");

    if (schedule == null) {
      out.println("해당 번호의 스케줄이 없습니다!");
    } else {

      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>스케줄</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>스케줄</h1>");

      out.println("<p>");
      if (loginUser.equals(schedule.getOwner())) {
        if (addParticipantNo != null) {
          addParticipant(schedule, addParticipantNo, out);
        }

        if (deleteParticipantNo != null) {
          deleteParticipant(schedule, deleteParticipantNo, out);
        }
      } else if (deleteParticipantNo != null
          && Integer.parseInt(deleteParticipantNo) == loginUser.getNo()) {
        deleteParticipant(schedule, deleteParticipantNo, out);
      } else {
        out.println("참가자 수정은 스캐줄 매니저 또는 본인만 가능합니다.");
      }
      out.println("</p>");


      out.println("<form action='/schedule/update' method='post'>");
      out.println("<table border='1'>");
      out
          .printf(
              "<tr><th style='width:120px;'>번호</th> "
                  + "<td style='width:300px;'><input type='text' name='no' value='%d' readonly='readonly'></td></tr>\n",
              schedule.getNo());
      out
          .printf(
              "<tr><th>제목</th><td><input type='text' name='title' value='%s'></td></tr>\n",
              schedule.getScheduleTitle());
      out
          .printf(
              "<tr><th>시작</th> <td><input type='datetime-local' name='start-time' value='%s'></td></tr>\n",
              schedule.getStartTime());
      out
          .printf(
              "<tr><th>종료</th> <td><input type='datetime-local' name='end-time' value='%s'></td></tr>\n",
              schedule.getEndTime());
      out.printf("<tr><th>스캐줄 매니저</th> <td>%s</td></tr>\n", schedule.getOwner().getName());
      out.println("</table>");
      out.println("<div>");
      out.println("<button>변경</button>");
      out.println("<button type='reset'>초기화</button>");
      if (schedule.getOwner().equals(loginUser)) {
        out.printf("<a href='/schedule/delete?no=%d'>삭제</a>\n", schedule.getNo());
      }
      out.printf("<a href='/schedule/list'>목록</a>\n");
      out.println("</div>");
      out.println("</form>");
      out.println("<div>");
      out.println("</div>");

      printParticipants(schedule, out, loginUser);

      if (loginUser.getNo() == schedule.getOwner().getNo()) {

        out.println("<br>");
        out.println("<div>");
        out.println("추가할 참가자 번호 입력");

        out.println("<form action='/schedule/detail' method='get'>");
        out.printf("<input type='hidden' name='no' value='%d'>", schedule.getNo());
        out.println("<input type='text' name='addParticipantNo'>");
        out.println("<button>추가</button>");
        out.println("</form>");

        out.println("</div>");
      }

      out.println("</body>");
      out.println("</html>");
    }

  }

  private void printParticipants(Schedule schedule, PrintWriter out, Member loginUser)
      throws IOException {

    List<Member> participantList =
        InitServlet.scheduleDao.findAllParticipatedMember(schedule.getNo());

    out.println("<br>");
    out.println("<p1>스케줄 참가자 명단</p1><br>");

    out.println("<table border='1'>");
    out.println("<tr>");
    out.println("<th style='width:100px;'>번호</th> <th style='width:100px;'>이름</th>");
    out.println("<th>-</th>");
    out.println("</tr>");

    for (Member m : participantList.toArray(new Member[0])) {

      out.println("<tr>");
      out.println("<form action='/schedule/detail' method='get'>");
      out.printf("<input type='hidden' name='no' value='%d'>", schedule.getNo());
      out.printf("<input type='hidden' name='deleteParticipantNo' value='%d'>", m.getNo());
      out
          .printf(
              "<td>%d</td> <td><a href='/member/detail?no=%d'>%s</a></td>\n",
              m.getNo(),
              m.getNo(),
              m.getName());
      out.println("<td>");
      if (loginUser.equals(schedule.getOwner()) || loginUser.equals(m)) {
        out.println("<button>삭제</button>");
      }
      out.println("</td>");
      out.println("</form>");
      out.println("</tr>");
    }
    out.println("</table>");
  }

  void addParticipant(Schedule schedule, String addParticipantNo, PrintWriter out) {
    try {
      int result =
          InitServlet.scheduleDao
              .addScheduleParticipant(schedule.getNo(), Integer.parseInt(addParticipantNo));

      if (result == -1) {
        out.println("이미 참가중인 멤버입니다.");
      } else if (result == -2) {
        out.println("존재하지 않는 멤버입니다.");
      } else {
        InitServlet.sqlSessionFactory.openSession(false).commit();
        out.println("참가자를 추가했습니다.");
      }

    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
    }
  }

  void deleteParticipant(Schedule schedule, String deleteParticipantNo, PrintWriter out) {
    try {
      int result =
          InitServlet.scheduleDao
              .deleteScheduleParticipant(schedule.getNo(), Integer.parseInt(deleteParticipantNo));

      if (result == -1) {
        out.println("참여하지 않는 멤버입니다.");
      } else if (result == -2) {
        out.println("존재하지 않는 멤버입니다.");
      } else {
        InitServlet.sqlSessionFactory.openSession(false).commit();
        out.println("참가자를 삭제했습니다.");
      }

    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
    }
  }
}


