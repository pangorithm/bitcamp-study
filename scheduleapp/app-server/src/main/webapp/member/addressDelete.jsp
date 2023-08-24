<%@ page
language="java"
pageEncoding="utf-8"
contentType="text/html;charset=utf-8"
trimDirectiveWhitespaces="true"
errorPage="/error.jsp"%>

<%@ page import="java.io.IOException"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.List"%>
<%@ page import="com.bitcamp.myapp.dao.MemberDao"%>
<%@ page import="com.bitcamp.myapp.vo.Member"%>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory"%>

<%
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");
    MemberDao memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    int mano = Integer.parseInt((String) request.getParameter("mano"));
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));

      request.setAttribute("refresh", "2;url=/member/detail.jsp?no=" + memberNo);

      memberDao.deleteMemberAddress(loginUser.getNo(), mano);
      sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("/member/detail.jsp?no=" + memberNo);
%>