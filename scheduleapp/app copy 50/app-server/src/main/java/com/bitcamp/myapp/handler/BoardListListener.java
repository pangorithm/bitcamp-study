package com.bitcamp.myapp.handler;

import java.util.List;
import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;

public class BoardListListener implements ActionListener {

  BoardDao boardDao;

  public BoardListListener(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    prompt.println("---------------------------------------");
    prompt.println("번호, 제목, 작성자, 조회수, 등록일");
    prompt.println("---------------------------------------");

    List<Board> list = boardDao.list();

    for (Board board : list) {
      prompt.printf("%d, %s, %s, %d, %tY-%5$tm-%5$td\n", board.getNo(), board.getTitle(),
          board.getWriter().getName(), board.getViewCount(), board.getCreatedDate());
    }
  }

}


