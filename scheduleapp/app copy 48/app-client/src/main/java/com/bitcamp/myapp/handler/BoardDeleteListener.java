package com.bitcamp.myapp.handler;

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
    Board b = new Board();
    b.setNo(prompt.inputInt("번호? "));
    b.setPassword(prompt.inputString("암호?"));
    if (boardDao.remove(b) == 0) {
      System.out.println("해당 번호의 게시글이 없거나 암호가 일치하지 않습니다!");
    }
    System.out.println("삭제했습니다.");
  }
}


