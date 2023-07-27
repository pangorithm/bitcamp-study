package com.bitcamp.myapp.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;

public class BoardAddListener implements ActionListener {

  BoardDao boardDao;
  SqlSessionFactory sqlSessionFactory;

  public BoardAddListener(BoardDao boardDao, SqlSessionFactory sqlSessionFactory) {
    this.boardDao = boardDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    Board board = new Board();
    board.setTitle(prompt.inputString("제목? "));
    board.setContent(prompt.inputString("내용? "));
    board.setWriter((Member) prompt.getAttribute("loginUser"));

    try {
      boardDao.insert(board);
      sqlSessionFactory.openSession(false).commit();

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }

}


