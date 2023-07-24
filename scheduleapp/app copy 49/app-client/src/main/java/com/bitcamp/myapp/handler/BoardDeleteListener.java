package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.ClientApp;
import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;

public class BoardDeleteListener implements ActionListener {

  BoardDao boardDao;

  public BoardDeleteListener(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Board board = boardDao.findBy(prompt.inputInt("번호? "));
    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }
    if (!board.getWriter().equals(ClientApp.loginUser)) {
      System.out.println("삭제 권한이 없습니다!");
      return;
    }
    System.out.println("삭제했습니다.");
  }
}


