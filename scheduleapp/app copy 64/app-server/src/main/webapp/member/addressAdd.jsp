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
<%@ page import="com.bitcamp.myapp.vo.AddressType"%>
<%@ page import="com.bitcamp.myapp.vo.MemberAddress"%>
<%@ page import="com.bitcamp.myapp.vo.AttachedFile"%>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory"%>


<%

    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");
    MemberDao memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    MemberAddress memberAddress = new MemberAddress();
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));
    AddressType addressType = new AddressType();

    request.setAttribute("refresh", "2;url=/member/detail.jsp?no=" + memberNo);

    memberAddress.setMno(memberNo);
    addressType.setNo(Integer.parseInt(request.getParameter("addressType")));
    memberAddress.setAddressType(addressType);
    memberAddress
        .setPostAddress(request.getParameter("postAddress").replaceAll("<script", "<scr!pt"));
    memberAddress
        .setBasicAddress(request.getParameter("basicAddr").replaceAll("<script", "<scr!pt"));
    memberAddress
        .setDetailAddress(request.getParameter("detailAddr").replaceAll("<script", "<scr!pt"));

      if (memberNo == loginUser.getNo()) {
        memberDao.insertMemberAddress(memberAddress);
        sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("/member/detail.jsp?no=" + memberNo);
      } else {
        throw new Exception("<p>로그인한 계정만 주소 추가 가능합니다</p>");
      }
%>