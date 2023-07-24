package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.ClientApp;
import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;

public class BoardUpdateListener implements ActionListener {

  BoardDao boardDao;

  public BoardUpdateListener(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    int boardNo = prompt.inputInt("번호? ");

    Board board = boardDao.findBy(boardNo);
    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }

    if (!board.getWriter().equals(ClientApp.loginUser)) {
      System.out.println("수정 권한이 없습니다!");
      return;
    }

    board.setTitle(prompt.inputString("제목(%s)? ", board.getTitle()));
    board.setContent(prompt.inputString("내용(%s)? ", board.getContent()));

    boardDao.update(board);
  }
}


