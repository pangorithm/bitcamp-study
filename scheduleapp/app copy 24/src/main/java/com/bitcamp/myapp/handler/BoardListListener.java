package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.vo.Board;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.List;

public class BoardListListener extends AbstractBoardListener {

  public BoardListListener(List<Board> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    for (Object obj : this.list.toArray()) {
      Board board = (Board) obj;
      System.out.printf("번호: %d, 제목: %s, 작성자: %s, 조회수: %d, 작성일: %tY-%5$tm-%5$td\n", board.getNo(),
          board.getTitle(), board.getWriter(), board.getViewCount(), board.getCreatedDate());
    }
  }
}
