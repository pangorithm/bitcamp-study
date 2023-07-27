package com.bitcamp.myapp.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;

public class BoardUpdateListener implements ActionListener {

  BoardDao boardDao;
  SqlSessionFactory sqlSessionFactory;

  public BoardUpdateListener(BoardDao boardDao, SqlSessionFactory sqlSessionFactory) {
    this.boardDao = boardDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    int boardNo = prompt.inputInt("번호? ");

    Board board = boardDao.findBy(boardNo);
    if (board == null) {
      prompt.println("해당 번호의 게시글이 없습니다!");
      return;
    }

    board.setTitle(prompt.inputString("제목(%s)? ", board.getTitle()));
    board.setContent(prompt.inputString("내용(%s)? ", board.getContent()));
    board.setWriter((Member) prompt.getAttribute("loginUser"));

    try {
      if (boardDao.update(board) == 0) {
        prompt.println("게시글 변경 권한이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        prompt.println("변경했습니다!");
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }

}


