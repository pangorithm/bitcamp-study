<%@ page
  language="java"
  pageEncoding="utf-8"
  contentType="text/html;charset=utf-8"
  trimDirectiveWhitespaces="true"
  errorPage="/error.jsp"
  %>

<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.bitcamp.util.NcpObjectStorageService"%>
<%@ page import="com.bitcamp.myapp.dao.MemberDao"%>
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

    Schedule sch = new Schedule();
    sch.setNo(Integer.parseInt(request.getParameter("no")));
    sch.setTitle(request.getParameter("title"));
    sch.setContent(request.getParameter("content"));
    sch.setStartTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("start-time"))));
    sch.setEndTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("end-time"))));
    sch.setOwner(loginUser);

      if (scheduleDao.update(sch) == 0) {
        throw new Exception("스케줄이 없거나 변경 권한이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list.jsp");
      }

%>
