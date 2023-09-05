package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.BoardService;
import com.bitcamp.myapp.vo.Board;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("/board/detail")
public class BoardDetailController {

  @Autowired
  BoardService boardService;

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    try {
      int category = Integer.parseInt(request.getParameter("category"));
      int no = Integer.parseInt(request.getParameter("no"));

      Board board = boardService.get(no);
      if (board != null) {
        boardService.increaseViewCount(board.getNo());
        request.setAttribute("board", board);
      }
      return "/WEB-INF/jsp/board/detail.jsp";

    } catch (Exception e) {
      request.setAttribute("refresh",
          "5;url=/board/list?category=" + request.getParameter("category"));
      throw e;
    }
  }
}











