<%@ page
    language="java"
    pageEncoding="utf-8"
    contentType="text/html;charset=utf-8"
    errorPage="/error.jsp"
    %>

<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.time.ZoneId"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.bitcamp.util.NcpObjectStorageService"%>
<%@ page import="com.bitcamp.myapp.dao.MemberDao"%>
<%@ page import="com.bitcamp.myapp.vo.Member"%>
<%@ page import="com.bitcamp.myapp.vo.Schedule"%>
<%@ page import="com.bitcamp.util.NcpObjectStorageService"%>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory"%>

<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<jsp:useBean id="scheduleDao" type="com.bitcamp.myapp.dao.ScheduleDao" scope="application"/>
<jsp:useBean id="loginUser" class="com.bitcamp.myapp.vo.Member" scope="session"/>


    <!DOCTYPE html>
    <html>
    <head>
    <meta charset='UTF-8'>
    <title>스케줄</title>
    </head>
    <body>

    <jsp:include page="../header.jsp"/>

    <h1>스케줄 검색 결과 </h1>
    <div style='margin:5px;'>
    <a href='/schedule/form.jsp'>새 스케줄</a>
    <a href='/schedule/searchForm.jsp'>스케줄 검색</a>
    </div>
    <table border='1'>
    <thead>
    <tr><th>번호</th> <th>제목</th> <th>시작</th> <th>종료</th> <th>스케줄 매니저</th></tr>
    </thead>
    <tbody>

<%
    request.setAttribute("refresh", "2;url=list.jsp");

    String searchTitle = request.getParameter("title");
    long searchRangeStart =
        localDateTimeToLong(LocalDateTime.parse(request.getParameter("start-time")));
    long searchRangeEnd =
        localDateTimeToLong(LocalDateTime.parse(request.getParameter("end-time")));

    List<Schedule> list = scheduleDao.findAllOwnedSchedule(loginUser);
    list.addAll(scheduleDao.findAllParticipatedSchedule(loginUser));

    Schedule[] schedules = list.toArray(new Schedule[0]);

    for (Schedule sch : schedules) {
      if ((searchTitle.length() > 0
          && sch.getTitle().matches(String.format(".*%s.*", searchTitle)))
          || (sch.getEndTime().getTime() > searchRangeStart
          && sch.getStartTime().getTime() < searchRangeEnd)) {

        out.println(String.format(
                "<tr><td>%d</td> <td><a href='/schedule/detail.jsp?no=%d'>%s</a></td> <td>%s</td> <td>%s</td> <td>%s</td></tr>\n",
                sch.getNo(),
                sch.getNo(),
                sch.getTitle(),
                sch.getStartTime().toString(),
                sch.getEndTime().toString(),
                sch.getOwner().getName()));
      }
    }
%>

    </tbody>
    </table>
    <a href='/schedule/list.jsp'>스케줄 목록</a>

    <jsp:include page="../footer.jsp"/>

    </body>
    </html>

<%!
  private long localDateTimeToLong(LocalDateTime localDateTime) {
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }
%>
