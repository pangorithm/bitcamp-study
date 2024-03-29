package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.vo.AttachedFile;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.myapp.vo.Member;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

@Component("/board/fileDelete")
public class BoardFileDeleteController implements PageController {

  BoardDao boardDao;
  SqlSessionFactory sqlSessionFactory;

  public BoardFileDeleteController(BoardDao boardDao, SqlSessionFactory sqlSessionFactory) {
    this.boardDao = boardDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/login";
    }

    Board board = null;
    try {
      int category = Integer.parseInt(request.getParameter("category"));
      int fileNo = Integer.parseInt(request.getParameter("no"));

      AttachedFile attachedFile = boardDao.findFileBy(fileNo);
      board = boardDao.findBy(category, attachedFile.getBoardNo());
      if (board.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("게시글 변경 권한이 없습니다!");
      }

      if (boardDao.deleteFile(fileNo) == 0) {
        throw new Exception("해당 번호의 첨부파일이 없거나 삭제 권한이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        return
            "redirect:detail?category=" + category + "&no=" + board.getNo();
      }

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh",
          "2;url=/board/detail?category=" + request.getParameter("category") +
              "&no=" + board.getNo());
      throw e;
    }
  }
}











