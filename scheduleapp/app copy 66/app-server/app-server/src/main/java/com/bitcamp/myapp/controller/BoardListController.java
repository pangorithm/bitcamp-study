package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.BoardDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/board/list")
public class BoardListController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      BoardDao boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
      request.setAttribute("list",
          boardDao.findAll(Integer.parseInt(request.getParameter("category"))));

      response.setContentType("text/html;charset=UTF-8");
      request.setAttribute("viewUrl", "/WEB-INF/jsp/board/list.jsp");

    } catch (Exception e) {
      request.setAttribute("refresh", "1;url=/");
      request.setAttribute("exception", e);
    }
  }

}











