<%@ page
  language="java"
  pageEncoding="utf-8"
  contentType="text/html;charset=utf-8"
  trimDirectiveWhitespaces="true"
  errorPage="/error.jsp"
  %>

<%@ page import="javax.servlet.ServletException"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashSet"%>
<%@ page import="com.bitcamp.util.NcpObjectStorageService"%>
<%@ page import="com.bitcamp.myapp.dao.ScheduleDao"%>
<%@ page import="com.bitcamp.myapp.vo.Member"%>
<%@ page import="com.bitcamp.myapp.vo.Schedule"%>
<%@ page import="com.bitcamp.util.NcpObjectStorageService"%>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory"%>

<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<jsp:useBean id="ncpObjectStorageService" type="com.bitcamp.util.NcpObjectStorageService" scope="application"/>
<jsp:useBean id="scheduleDao" type="com.bitcamp.myapp.dao.ScheduleDao" scope="application"/>
<jsp:useBean id="loginUser" class="com.bitcamp.myapp.vo.Member" scope="session"/>

<%
    request.setAttribute("refresh", "2;url=list.jsp");

    Schedule schedule =
        scheduleDao.findBy(Integer.parseInt((String) request.getParameter("no")));

    String addParticipantNo = (String) request.getParameter("addParticipantNo");
    String deleteParticipantNo = (String) request.getParameter("deleteParticipantNo");

    if (schedule == null) {
      request.setAttribute("message", "해당 번호의 스케줄이 없습니다!");
      request.setAttribute("refresh", "2;url=list.jsp");

      request.getRequestDispatcher("/error.jsp").forward(request, response);
    } else {

      List<Member> participantList =
          scheduleDao.findAllParticipatedMember(schedule.getNo());
      HashSet<Member> memberSet = new HashSet<>();
      memberSet.add(schedule.getOwner());
      for (Member p : participantList) {
        memberSet.add(p);
      }

      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>스케줄</title>");
      out.println("</head>");
      out.println("<body>");

      request.getRequestDispatcher("/header.jsp").include(request, response);

      out.println("<h1>스케줄</h1>");

      out.println("<p>");
      if (loginUser.equals(schedule.getOwner())) {
        if (addParticipantNo != null) {

            // addParticipant
          try {
                int result =
                    scheduleDao
                        .addScheduleParticipant(schedule.getNo(), Integer.parseInt(addParticipantNo));

                if (result == -1) {
                  throw new Exception("이미 참가중인 멤버입니다.");
                } else if (result == -2) {
                  throw new Exception("존재하지 않는 멤버입니다.");
                } else {
                  sqlSessionFactory.openSession(false).commit();
                }

              } catch (Exception e) {
                sqlSessionFactory.openSession(false).rollback();
                request.setAttribute("error", e);
                request.setAttribute("message", e.getMessage());
                request.setAttribute("refresh", "2;url=detail.jsp?no=" + schedule.getNo());

                request.getRequestDispatcher("/error.jsp").forward(request, response);
              }
            //

          participantList = scheduleDao.findAllParticipatedMember(schedule.getNo());
        }

        if (deleteParticipantNo != null) {

           //deleteParticipant
          try {
                int result =
                    scheduleDao
                        .deleteScheduleParticipant(schedule.getNo(), Integer.parseInt(deleteParticipantNo));

                if (result == -1) {
                  throw new Exception("참여하지 않는 멤버입니다.");
                } else if (result == -2) {
                  throw new Exception("존재하지 않는 멤버입니다.");
                } else {
                  sqlSessionFactory.openSession(false).commit();
                }

              } catch (Exception e) {
                sqlSessionFactory.openSession(false).rollback();
                request.setAttribute("error", e);
                request.setAttribute("message", e.getMessage());
                request.setAttribute("refresh", "2;url=detail.jsp?no=" + schedule.getNo());

                request.getRequestDispatcher("/error.jsp").forward(request, response);
              }
            //

          participantList = scheduleDao.findAllParticipatedMember(schedule.getNo());
        }
      } else if (deleteParticipantNo != null
          && Integer.parseInt(deleteParticipantNo) == loginUser.getNo()) {

          //deleteParticipant
        try {
              int result =
                  scheduleDao
                      .deleteScheduleParticipant(schedule.getNo(), Integer.parseInt(deleteParticipantNo));

              if (result == -1) {
                throw new Exception("참여하지 않는 멤버입니다.");
              } else if (result == -2) {
                throw new Exception("존재하지 않는 멤버입니다.");
              } else {
                sqlSessionFactory.openSession(false).commit();
              }

            } catch (Exception e) {
              sqlSessionFactory.openSession(false).rollback();
              request.setAttribute("error", e);
              request.setAttribute("message", e.getMessage());
              request.setAttribute("refresh", "2;url=detail.jsp?no=" + schedule.getNo());

              request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
          //

        participantList = scheduleDao.findAllParticipatedMember(schedule.getNo());
      } else if (addParticipantNo != null) {
        out.println("참가자 수정은 스캐줄 매니저 또는 본인만 가능합니다.");
      }
      out.println("</p>");

      if (memberSet.contains(loginUser)) {
        out.println("<form action='/schedule/update.jsp' method='post'>");
        out.println("<table border='1'>");
        out.println(String.format(
                "<tr><th style='width:120px;'>번호</th> "
                    + "<td style='width:300px;'><input type='text' name='no' value='%d' readonly='readonly'></td></tr>\n",
                schedule.getNo()));
        out.println(String.format(
                "<tr><th>제목</th><td><input type='text' name='title' value='%s'></td></tr>\n",
                schedule.getTitle()));
        out.println(String.format(
                "<tr><th>제목</th><td><input type='text' name='content' value='%s'></td></tr>\n",
                schedule.getContent()));
        out.println(String.format(
                "<tr><th>시작</th> <td><input type='datetime-local' name='start-time' value='%s'></td></tr>\n",
                schedule.getStartTime()));
        out.println(String.format(
                "<tr><th>종료</th> <td><input type='datetime-local' name='end-time' value='%s'></td></tr>\n",
                schedule.getEndTime()));
        out.println(String.format(
                "<tr><th>스캐줄 매니저</th> <td><a href='../member/detail.jsp?no=%d'>%s</a></td></tr>\n",
                schedule.getOwner().getNo(),
                schedule.getOwner().getName()));
        out.println("</table>");
        out.println("<div>");
        out.println("<button>변경</button>");
        out.println("<button type='reset'>초기화</button>");
        if (schedule.getOwner().equals(loginUser)) {
          out.println(String.format("<a href='/schedule/delete.jsp?no=%d'>삭제</a>\n", schedule.getNo()));
        }
        out.println(String.format("<a href='/schedule/list.jsp'>목록</a>\n"));
        out.println("</div>");
        out.println("</form>");
        out.println("<div>");
        out.println("</div>");

        out.println("<br>");
            out.println("<p1>스케줄 참가자 명단</p1><br>");

            out.println("<table border='1'>");
            out.println("<tr>");
            out.println("<th style='width:100px;'>번호</th> <th style='width:100px;'>이름</th>");
            out.println("<th>-</th>");
            out.println("</tr>");

            for (Member m : participantList.toArray(new Member[0])) {

              out.println("<tr>");
              out.println("<form action='/schedule/detail.jsp' method='get'>");
              out.println(String.format("<input type='hidden' name='no' value='%d'>", schedule.getNo()));
              out.println(String.format("<input type='hidden' name='deleteParticipantNo' value='%d'>", m.getNo()));
              out.println(String.format(
                      "<td>%d</td> <td><a href='/member/detail.jsp?no=%d'>%s</a></td>\n",
                      m.getNo(),
                      m.getNo(),
                      m.getName()));
              out.println("<td>");
              if (loginUser.equals(schedule.getOwner()) || loginUser.equals(m)) {
                out.println("<button>삭제</button>");
              }
              out.println("</td>");
              out.println("</form>");
              out.println("</tr>");
            }
            out.println("</table>");

        if (loginUser.getNo() == schedule.getOwner().getNo()) {

          out.println("<br>");
          out.println("<div>");
          out.println("추가할 참가자 번호 입력");

          out.println("<form action='/schedule/detail.jsp' method='get'>");
          out.println(String.format("<input type='hidden' name='no' value='%d'>", schedule.getNo()));
          out.println("<input type='number' name='addParticipantNo' min='1'>");
          out.println("<button>추가</button>");
          out.println("</form>");

          out.println("</div>");
        }
      } else {
        out.println("<div>");
        out.println("스캐줄 매니저 또는 참가자만 조회 가능합니다.");
        out.println("</div>");

        response.sendRedirect("/schedule/list.jsp");
        return;
      }

      request.getRequestDispatcher("/footer.jsp").include(request, response);

      out.println("</body>");
      out.println("</html>");
    }

%>

