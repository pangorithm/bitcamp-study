package bitcamp.myapp.handler;

import java.io.IOException;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public class BoardDeleteListener implements ActionListener {

  BoardDao boardDao;

  public BoardDeleteListener(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {

    Board b = new Board();
    b.setNo(prompt.inputInt("번호? "));
    b.setWriter((Member) prompt.getAttribute("loginUser"));

    if (boardDao.remove(b) == 0) {
      prompt.println("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
    }
    prompt.println("삭제했습니다.");
  }
}


