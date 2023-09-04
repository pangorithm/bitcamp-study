package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.BoardDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;


@Component("/board/list")
public class BoardListController implements PageController {

  BoardDao boardDao;

  public BoardListController(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    try {
      request.setAttribute("list",
          boardDao.findAll(Integer.parseInt(request.getParameter("category"))));
      return "/WEB-INF/jsp/board/list.jsp";

    } catch (Exception e) {
      request.setAttribute("refresh", "1;url=/");
      throw e;
    }
  }

}











