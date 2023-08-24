<%@ page
    language="java"
    pageEncoding="UTF-8"
    contentType="text/html;charset=UTF-8"
    trimDirectiveWhitespaces="true"
    errorPage="/error.jsp" %>

<%@ page import="java.io.IOException"%>
<%@ page import="java.util.ArrayList"%>
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

    Member member = new Member();
    member.setNo(Integer.parseInt(request.getParameter("no")));
    member.setName(request.getParameter("username"));
    member.setEmail(request.getParameter("email"));
    member.setTel(request.getParameter("tel"));
    member.setPassword(request.getParameter("password"));
    member.setGender(request.getParameter("gender").charAt(0));

    Part photoPart = request.getPart("photo");
    if (photoPart.getSize() > 0) {
      String uploadFileUrl = ncpObjectStorageService.uploadFile(
          "bitcamp-nc7-bucket-14", "member/", photoPart);
      member.setPhoto(uploadFileUrl);
    }

    if (memberDao.update(member) == 0) {
      throw new Exception("회원이 없습니다.");
    } else {
      sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("list.jsp");
    }

%>
