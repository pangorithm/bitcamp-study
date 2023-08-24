<%@ page
language="java"
pageEncoding="utf-8"
contentType="text/html;charset=utf-8"
trimDirectiveWhitespaces="true"
errorPage="../error.jsp"%>

<%@ page import="java.io.IOException"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.bitcamp.util.NcpObjectStorageService"%>
<%@ page import="com.bitcamp.myapp.dao.MemberDao"%>
<%@ page import="com.bitcamp.myapp.vo.Member"%>
<%@ page import="com.bitcamp.util.NcpObjectStorageService"%>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory"%>

<%
    request.setAttribute("refresh", "2;url=list.jsp");

    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");
    MemberDao memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");
    NcpObjectStorageService ncpObjectStorageService = (NcpObjectStorageService) this.getServletContext()
        .getAttribute("ncpObjectStorageService");

    Member m = new Member();
    m.setName(request.getParameter("name"));
    m.setEmail(request.getParameter("email"));
    m.setPassword(request.getParameter("password"));
    m.setGender(request.getParameter("gender").charAt(0));
    m.setTel(request.getParameter("tel"));

    Part photoPart = request.getPart("photo");
    if (photoPart.getSize() > 0) {
      String uploadFileUrl = ncpObjectStorageService.uploadFile(
          "bitcamp-nc7-bucket-14", "member/", photoPart);
      m.setPhoto(uploadFileUrl);
    }

      memberDao.insert(m);
      sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("list.jsp");
%>
