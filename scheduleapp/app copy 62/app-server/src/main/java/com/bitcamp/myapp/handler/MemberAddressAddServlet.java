package com.bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.AddressType;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.MemberAddress;

@WebServlet("/member/address/add")
public class MemberAddressAddServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    MemberAddress memberAddress = new MemberAddress();
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));
    AddressType addressType = new AddressType();

    memberAddress.setMno(memberNo);
    addressType.setNo(Integer.parseInt(request.getParameter("addressType")));
    memberAddress.setAddressType(addressType);
    memberAddress
        .setPostAddress(request.getParameter("postAddress").replaceAll("<script", "<scr!pt"));
    memberAddress
        .setBasicAddress(request.getParameter("basicAddr").replaceAll("<script", "<scr!pt"));
    memberAddress
        .setDetailAddress(request.getParameter("detailAddr").replaceAll("<script", "<scr!pt"));

    try {
      if (memberNo == loginUser.getNo()) {
        InitServlet.memberDao.insertMemberAddress(memberAddress);
        InitServlet.sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("/member/detail?no=" + memberNo);
      } else {
        throw new Exception("<p>로그인한 계정만 주소 추가 가능합니다</p>");
      }
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=/member/detail?no=" + memberNo);

      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}
