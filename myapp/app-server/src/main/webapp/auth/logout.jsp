<%@ page
    language="java"
    pageEncoding="UTF-8"
    contentType="text/html;charset=UTF-8"
    trimDirectiveWhitespaces="true"
    errorPage="/error.jsp" %>

<%@ page import="bitcamp.myapp.dao.MemberDao"%>
<%@ page import="bitcamp.myapp.vo.Member"%>

<%
    session.invalidate();
    response.sendRedirect("/");
%>