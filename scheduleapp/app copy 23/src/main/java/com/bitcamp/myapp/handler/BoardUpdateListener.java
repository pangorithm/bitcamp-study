package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.vo.Board;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.List;

public class BoardUpdateListener extends AbstractBoardListener {

  public BoardUpdateListener(List list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Board board = (Board) this.list.get(findBy(prompt.inputInt("번호?")));
    if (board == null) {
      System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
    } else {
      if (board.getPassword().equals(prompt.inputString("암호?"))) {
        board.setTitle(prompt.inputString("제목? (%s)\n", board.getTitle()));
        board.setContent(prompt.inputString("내용? (%s)\n", board.getContent()));
        board.setCreatedDate(System.currentTimeMillis());
      } else {
        System.out.println("암호가 올바르지 않습니다.");
      }
    }
  }


}
