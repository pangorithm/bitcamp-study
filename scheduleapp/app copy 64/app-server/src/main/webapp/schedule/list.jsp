<%@ page
    language="java"
    pageEncoding="utf-8"
    contentType="text/html;charset=utf-8"
    errorPage="/error.jsp"
    %>

<%@ page import="java.io.IOException"%>
<%@ page import="java.util.List"%>
<%@ page import="com.bitcamp.myapp.vo.Member"%>
<%@ page import="com.bitcamp.myapp.vo.Schedule"%>


<jsp:useBean id="scheduleDao" type="com.bitcamp.myapp.dao.ScheduleDao" scope="application"/>
<jsp:useBean id="loginUser" class="com.bitcamp.myapp.vo.Member" scope="session"/>

<%
    if (loginUser.getNo() == 0) {
      response.sendRedirect("/auth/form.jsp");
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
    <a href='/schedule/searchForm.jsp'>스케줄 검색</a>
    </div>
    <table border='1'>
    <thead>
    <tr><th>번호</th> <th>제목</th> <th>시작</th> <th>종료</th> <th>스캐줄 매니저</th></tr>
    </thead>
    <tbody>

    <tr><th><관리 스케줄></th></tr>

<%
    for (Schedule sch : scheduleDao.findAllOwnedSchedule(loginUser).toArray(new Schedule[0])) {
      pageContext.setAttribute("sch", sch);
%>
      <tr>
        <td>${sch.getNo()}</td>
        <td>
          <a href='/schedule/detail.jsp?no=${sch.getNo()}'>
            ${(sch.getTitle() == null || sch.getTitle().length() == 0
                || sch.getTitle().matches("^\\s+$")) ? "제목없음" : sch.getTitle()}
          </a>
        </td>
        <td>${sch.getStartTime().toString()}</td>
        <td>${sch.getEndTime().toString()}</td>
        <td>${sch.getOwner().getName()}</td>
      </tr>
<%
    }
%>

    <tr><th><참가 스케줄></th></tr>

<%
    for (Schedule sch : scheduleDao.findAllParticipatedSchedule(loginUser).toArray(new Schedule[0])) {
      pageContext.setAttribute("sch", sch);
%>
      <tr>
        <td>${sch.getNo()}</td>
        <td>
          <a href='/schedule/detail.jsp?no=${sch.getNo()}'>
            ${(sch.getTitle() == null || sch.getTitle().length() == 0
                || sch.getTitle().matches("^\\s+$")) ? "제목없음" : sch.getTitle()}
          </a>
        </td>
        <td>${sch.getStartTime().toString()}</td>
        <td>${sch.getEndTime().toString()}</td>
        <td>${sch.getOwner().getName()}</td>
      </tr>
<%
    }
%>

    </tbody>
    </table>
    <a href='/'>메인</a>

<jsp:include page="../footer.jsp"/>

    </body>
    </html>
