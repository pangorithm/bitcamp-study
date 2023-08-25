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

    Schedule sch = scheduleDao.findBy(Integer.parseInt(request.getParameter("no")));

      if (sch == null) {
        throw new Exception("해당 번호의 스케줄이 없습니다!");
      }

      if (!sch.getOwner().equals(loginUser)) {
        throw new Exception("삭제 권한이 없습니다.");
      }

      scheduleDao.deleteAllScheduleParticipant(sch.getNo());
      scheduleDao.delete(sch);
      sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("/schedule/list.jsp");
%>
