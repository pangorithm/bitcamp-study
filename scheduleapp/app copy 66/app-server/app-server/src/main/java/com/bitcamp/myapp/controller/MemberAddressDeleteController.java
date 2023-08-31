package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.MemberDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;
import org.apache.ibatis.session.SqlSessionFactory;

@WebServlet("/member/addressDelete")
public class MemberAddressDeleteController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");
    MemberDao memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    int mano = Integer.parseInt((String) request.getParameter("mano"));
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));

    try {
      memberDao.deleteMemberAddress(loginUser.getNo(), mano);
      sqlSessionFactory.openSession(false).commit();
      request.setAttribute("viewUrl", "redirect:detail?no=" + memberNo);
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=/member/detail?no=" + memberNo);
      request.setAttribute("exception", e);
    }
  }

}
