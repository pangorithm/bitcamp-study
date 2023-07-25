package com.bitcamp.myapp.handler;

import java.io.IOException;
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
  public void service(BreadcrumbPrompt prompt) throws IOException {
    Board board = boardDao.findBy(prompt.inputInt("번호? "));
    if (board == null) {
      prompt.println("해당 번호의 게시글이 없습니다!");
      return;
    }
    if (!board.getWriter().equals(prompt.getAttribute("loginUser"))) {
      prompt.println("삭제 권한이 없습니다!");
      return;
    }
    prompt.println("삭제했습니다.");
  }
}


