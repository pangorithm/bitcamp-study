<%@ page
    language="java"
    pageEncoding="utf-8"
    contentType="text/html;charset=utf-8"
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
<jsp:useBean id="scheduleDao" type="com.bitcamp.myapp.dao.ScheduleDao" scope="application"/>
<jsp:useBean id="loginUser" class="com.bitcamp.myapp.vo.Member" scope="session"/>

<%
    request.setAttribute("refresh", "2;url=list.jsp");

    if (loginUser.getNo() == 0) {
      response.sendRedirect("/auth/form.jsp");
      return;
    }

    Schedule sch = new Schedule();
    sch.setTitle(request.getParameter("title"));
    sch.setContent(request.getParameter("content"));
    sch.setStartTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("start-time"))));
    sch.setEndTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("end-time"))));
    sch.setOwner((Member) request.getSession().getAttribute("loginUser"));

      scheduleDao.insert(sch);
      // 새로 생성된 스케줄 번호를 알아야함
      // scheduleDao.scheduleAddParticipant(0, ((Member) prompt.getAttribute("loginUser")).getNo());
      sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("list.jsp");
%>

