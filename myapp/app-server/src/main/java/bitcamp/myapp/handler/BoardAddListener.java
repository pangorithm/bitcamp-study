package bitcamp.myapp.handler;

import java.io.IOException;
import java.sql.Connection;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public class BoardAddListener implements ActionListener {

  BoardDao boardDao;
  Connection con;

  public BoardAddListener(BoardDao boardDao, Connection con) {
    this.boardDao = boardDao;
    this.con = con;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    Board board = new Board();
    board.setTitle(prompt.inputString("제목? "));
    board.setContent(prompt.inputString("내용? "));
    board.setWriter((Member) prompt.getAttribute("loginUser"));

    try {
      boardDao.insert(board);
      Thread.currentThread().sleep(5000);
      boardDao.insert(board);
      Thread.currentThread().sleep(5000);
      boardDao.insert(board);
      Thread.currentThread().sleep(5000);
      con.commit();
    } catch (Exception e) {
      try {
        con.rollback();
      } catch (Exception e2) {
        // TODO: handle exception
      }
      throw new RuntimeException(e);
    }
  }

}


