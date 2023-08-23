package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.util.NcpObjectStorageService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.AttachedFile;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.myapp.vo.Member;
import org.apache.ibatis.session.SqlSessionFactory;

@WebServlet("/board/file/delete")
public class BoardFileDeleteServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");
    BoardDao boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    int category = Integer.parseInt((String) request.getParameter("category"));
    int fileNo = Integer.parseInt((String) request.getParameter("no"));

    // 첨부파일 번호로 첨부파일 데이터를 가져온다.
    AttachedFile attachedFile = boardDao.findFileBy(fileNo);
    // System.out.println(attachedFile);

    // 첨부파일 데이터에 있는 게시글 번호로 게시글 데이터를 가져온다.
    Board board = boardDao.findBy(category, attachedFile.getBoardNo());
    System.out.println(board);

    // // 게시글 데이터의 작성자와 로그인한 작성자가 일치하는지 검사한다.
    if (board.getWriter().getNo() != loginUser.getNo()) {
      throw new ServletException("게시글 변경 권한이 없습니다.");
    }

    // 일치하면 첨부파일을 삭제한다.
    try {
      if (boardDao.deleteFile(fileNo) == 0) {
        throw new Exception("해당 번호의 첨부파일이 없거나 삭제 권한이 없습니다.");
      } else {
        response.sendRedirect("/board/detail?category=" + category + "&no=" + board.getNo());
      }
      sqlSessionFactory.openSession(false).commit();
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }
}


