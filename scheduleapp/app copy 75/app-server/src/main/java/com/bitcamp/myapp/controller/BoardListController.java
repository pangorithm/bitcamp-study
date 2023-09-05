package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.BoardService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller("/board/list")
public class BoardListController {

  @Autowired
  BoardService boardService;

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    try {
      request.setAttribute("list",
          boardService.list(Integer.parseInt(request.getParameter("category"))));
      return "/WEB-INF/jsp/board/list.jsp";

    } catch (Exception e) {
      request.setAttribute("refresh", "1;url=/");
      throw e;
    }
  }

}











