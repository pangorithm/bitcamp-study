package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.vo.Board;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.List;

public abstract class AbstractBoardListener implements ActionListener {

  protected List list;

  public AbstractBoardListener(List list) {
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
