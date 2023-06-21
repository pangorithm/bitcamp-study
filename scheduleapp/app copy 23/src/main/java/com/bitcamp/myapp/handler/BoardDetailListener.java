package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.vo.Board;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.List;

public class BoardDetailListener extends AbstractBoardListener {

  public BoardDetailListener(List list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Board board = (Board) this.list.get(findBy(prompt.inputInt("번호?")));
    if (board == null) {
      System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
    } else {
      System.out.printf("제목: %s\n", board.getTitle());
      System.out.printf("내용: %s\n", board.getContent());
      System.out.printf("작성자: %s\n", board.getWriter());
      System.out.printf("조회수: %d\n", board.getViewCount());
      System.out.printf("작성일: %tY-%1$tm-%1$td\n", board.getCreatedDate());
      board.setViewCount(board.getViewCount() + 1);
    }
  }


}
