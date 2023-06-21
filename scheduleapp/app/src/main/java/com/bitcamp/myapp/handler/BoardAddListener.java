package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.vo.Board;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.List;

public class BoardAddListener extends AbstractBoardListener {

  public BoardAddListener(List list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Board board = new Board();
    board.setTitle(prompt.inputString("제목?\n"));
    board.setContent(prompt.inputString("내용?\n"));
    board.setWriter(prompt.inputString("작성자\n"));
    board.setPassword(prompt.inputString("암호\n"));
    this.list.add(board);
  }

}
