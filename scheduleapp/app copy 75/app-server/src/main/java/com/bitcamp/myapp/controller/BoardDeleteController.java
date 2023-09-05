package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.BoardService;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.myapp.vo.Member;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("/board/delete")
public class BoardDeleteController {

  @Autowired
  BoardService boardService;

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/login";
    }

    try {
      Board board = boardService.get(Integer.parseInt(request.getParameter("no")));

      if (board == null || board.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
      }

      boardService.delete(board.getNo());

      return "redirect:list?category=" + request.getParameter("category");


    } catch (Exception e) {
      request.setAttribute("refresh", "2;url=list?category=" + request.getParameter("category"));
      throw e;
    }
  }
}











