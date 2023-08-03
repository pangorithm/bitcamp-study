package com.bitcamp.myapp.handler;

import java.io.PrintWriter;
import org.apache.ibatis.session.SqlSessionFactory;
import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.util.Component;
import com.bitcamp.util.HttpServletRequest;
import com.bitcamp.util.HttpServletResponse;
import com.bitcamp.util.Servlet;

@Component("/board/update")
public class BoardUpdateServlet implements Servlet {

  BoardDao boardDao;
  SqlSessionFactory sqlSessionFactory;

  public BoardUpdateServlet(BoardDao boardDao, SqlSessionFactory sqlSessionFactory) {
    this.boardDao = boardDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    int category = Integer.parseInt((String) request.getParameter("category"));
    Board board = new Board();
    board.setNo(Integer.parseInt(request.getParameter("no")));
    board.setTitle(request.getParameter("title"));
    board.setContent(request.getParameter("content"));
    board.setWriter(loginUser);
    board.setCategory(category);

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.printf("<meta http-equiv='refresh' content='1;url=/board/list?category=%d'>\n", category);
    out.println("<title>게시글</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글 변경</h1>");

    try {
      if (boardDao.update(board) == 0) {
        out.println("게시글이 없거나 변경 권한이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        out.println("변경했습니다!");
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      out.println("게시글 변경 실패입니다!");
      e.printStackTrace();
    }
    out.println("</body>");
    out.println("</html>");
  }

}


