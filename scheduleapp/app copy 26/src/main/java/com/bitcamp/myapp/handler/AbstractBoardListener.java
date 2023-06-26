package com.bitcamp.myapp.handler;

import java.util.List;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.util.ActionListener;

public abstract class AbstractBoardListener implements ActionListener {

  protected List<Board> list;

  public AbstractBoardListener(List<Board> list) {
    this.list = list;
  }

  protected int findBy(int no) {
    Object[] list = this.list.toArray();
    for (int i = 0; i < this.list.size(); i++) {
      Board b = (Board) list[i];
      if (b.getNo() == no) {
        return i;
      }
    }
    return -1;
  }
}
