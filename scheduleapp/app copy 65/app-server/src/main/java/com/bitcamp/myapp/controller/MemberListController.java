package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.MemberDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;

@WebServlet("/member/list")
public class MemberListController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    MemberDao memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");
    request.setAttribute("list", memberDao.findAll());

    response.setContentType("text/html;charset=UTF-8");
    request.getRequestDispatcher("/WEB-INF/jsp/member/list.jsp").include(request, response);
  }

}