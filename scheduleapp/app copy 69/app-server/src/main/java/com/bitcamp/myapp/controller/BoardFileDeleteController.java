package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.vo.AttachedFile;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.myapp.vo.Member;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component("/board/fileDelete")
public class BoardFileDeleteController implements PageController {

  BoardDao boardDao;
  PlatformTransactionManager txManager;

  public BoardFileDeleteController(BoardDao boardDao, PlatformTransactionManager txManager) {
    this.boardDao = boardDao;
    this.txManager = txManager;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/login";
    }

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    Board board = null;
    try {
      int category = Integer.parseInt(request.getParameter("category"));
      int fileNo = Integer.parseInt(request.getParameter("no"));

      AttachedFile attachedFile = boardDao.findFileBy(fileNo);
      board = boardDao.findBy(attachedFile.getBoardNo());
      if (board.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("게시글 변경 권한이 없습니다!");
      }

      if (boardDao.deleteFile(fileNo) == 0) {
        throw new Exception("해당 번호의 첨부파일이 없거나 삭제 권한이 없습니다.");
      } else {
        txManager.commit(status);
        return
            "redirect:detail?no=" + board.getNo();
      }

    } catch (Exception e) {
      txManager.rollback(status);
      request.setAttribute("refresh",
          "2;url=/board/detail?category=" + request.getParameter("category") +
              "&no=" + board.getNo());
      throw e;
    }
  }
}











