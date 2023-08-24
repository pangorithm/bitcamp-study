<%@ page
    language="java"
    pageEncoding="utf-8"
    contentType="text/html;charset=utf-8"%>

<%@ page import="java.io.IOException"%>
<%@ page import="java.util.List"%>
<%@ page import="com.bitcamp.myapp.dao.ScheduleDao"%>
<%@ page import="com.bitcamp.myapp.vo.Member"%>
<%@ page import="com.bitcamp.myapp.vo.Schedule"%>


<%
    ScheduleDao scheduleDao = (ScheduleDao) this.getServletContext().getAttribute("scheduleDao");

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form");
      return;
    }
%>

    <!DOCTYPE html>
    <html>
    <head>
    <meta charset='UTF-8'>
    <title>스케줄</title>
    </head>
    <body>

<jsp:include page="../header.jsp"/>

    <h1>스케줄 목록 </h1>
    <div style='margin:5px;'>
    <a href='/schedule/form.jsp'>새 스케줄</a>
    <a href='/schedule/search.jsp'>스케줄 검색</a>
    </div>
    <table border='1'>
    <thead>
    <tr><th>번호</th> <th>제목</th> <th>시작</th> <th>종료</th> <th>스캐줄 매니저</th></tr>
    </thead>
    <tbody>

    <tr><th><관리 스케줄></th></tr>

<%
    for (Object obj : scheduleDao.findAllOwnedSchedule(loginUser).toArray()) {
      Schedule sch = (Schedule) obj;
      out.println(String.format(
              "<tr><td>%d</td> <td><a href='/schedule/detail?no=%d'>%s</a></td> <td>%s</td> <td>%s</td> <td>%s</td></tr>\n",
              sch.getNo(),
              sch.getNo(),
              sch.getTitle().matches("^\\s+$") ? "제목없음" : sch.getTitle(),
              sch.getStartTime().toString(),
              sch.getEndTime().toString(),
              sch.getOwner().getName()));
    }
%>

    <tr><th><참가 스케줄></th></tr>

<%
    for (Object obj : scheduleDao.findAllParticipatedSchedule(loginUser).toArray()) {
      Schedule sch = (Schedule) obj;
      out.println(String.format(
              "<tr><td>%d</td> <td><a href='/schedule/detail?no=%d'>%s</a></td> <td>%s</td> <td>%s</td> <td>%s</td></tr>\n",
              sch.getNo(),
              sch.getNo(),
              sch.getTitle(),
              sch.getStartTime().toString(),
              sch.getEndTime().toString(),
              sch.getOwner().getName()));
    }
%>

    </tbody>
    </table>
    <a href='/'>메인</a>

<jsp:include page="../footer.jsp"/>

    </body>
    </html>
