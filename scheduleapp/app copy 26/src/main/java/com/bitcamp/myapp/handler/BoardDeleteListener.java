package com.bitcamp.myapp.handler;

import java.util.List;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.util.BreadcrumbPrompt;

public class BoardDeleteListener extends AbstractBoardListener {

  public BoardDeleteListener(List<Board> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Board board = this.list.get(findBy(prompt.inputInt("번호?")));
    if (board == null) {
      System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
    } else {
      if (board.getPassword().equals(prompt.inputString("암호?"))) {
        this.list.remove(board);
      } else {
        System.out.println("암호가 올바르지 않습니다.");
      }
    }
  }


}
