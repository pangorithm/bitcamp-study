package com.bitcamp.myapp.handler;

import java.io.IOException;
import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.DataSource;

public class BoardDeleteListener implements ActionListener {

  BoardDao boardDao;
  DataSource ds;

  public BoardDeleteListener(BoardDao boardDao, DataSource ds) {
    this.boardDao = boardDao;
    this.ds = ds;
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

    try {
      boardDao.remove(board);
      ds.getConnection().commit();
    } catch (Exception e) {
      try {
        ds.getConnection().rollback();
      } catch (Exception e2) {
        // TODO: handle exception
      }
    }
    prompt.println("삭제했습니다.");
  }
}


