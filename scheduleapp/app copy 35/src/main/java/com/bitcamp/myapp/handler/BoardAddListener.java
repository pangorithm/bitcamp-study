package com.bitcamp.myapp.handler;

import java.util.List;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.util.BreadcrumbPrompt;

public class BoardAddListener extends AbstractBoardListener {

  public BoardAddListener(List<Board> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Board board = new Board();
    board.setNo(Board.boardId++);
    board.setTitle(prompt.inputString("제목?\n"));
    board.setContent(prompt.inputString("내용?\n"));
    board.setWriter(prompt.inputString("작성자\n"));
    board.setPassword(prompt.inputString("암호\n"));
    board.setCreatedDate(System.currentTimeMillis());

    this.list.add(board);
  }

}
