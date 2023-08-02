package com.bitcamp.myapp.handler;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import org.apache.ibatis.session.SqlSessionFactory;
import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.util.Component;
import com.bitcamp.util.HttpServletRequest;
import com.bitcamp.util.HttpServletResponse;
import com.bitcamp.util.Servlet;

@Component("/board/detail")
public class BoardDetailServlet implements Servlet {

  BoardDao boardDao;
  SqlSessionFactory sqlSessionFactory;

  SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd a hh:mm");

  public BoardDetailServlet(BoardDao boardDao, SqlSessionFactory sqlSessionFactory) {
    this.boardDao = boardDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Board board =
        boardDao.findBy(
            Integer.parseInt((String) request.getParameter("category")),
            Integer.parseInt((String) request.getParameter("no")));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>게시글</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글</h1>");

    if (board == null) {
      out.println("<p>해당 번호의 게시글이 없습니다!</p>");

    } else {
      out.println("<form action='/board/update'>");
      out.printf("<input type='hidden' name='category' value='%d'>\n", board.getCategory());
      out.println("<table border='1'>");
      out.printf(
          "<tr><th style='width:120px;'>번호</th> "
              + "<td style='width:300px;'><input type='text' name='no' value='%d' readonly='readonly'></td></tr>\n",
          board.getNo());
      out.printf(
          "<tr><th>제목</th><td><input type='text' name='title' value='%s'></td></tr>\n",
          board.getTitle());
      out.printf(
          "<tr><th>내용</th> <td><textarea name='content' style='height:200px; width:400px;'>%s</textarea></td></tr>\n",
          board.getContent());
      out.printf("<tr><th>작성자</th> <td>%s</td></tr>\n", board.getWriter().getName());
      out.printf("<tr><th>조회수</th> <td>%s</td></tr>\n", board.getViewCount());
      out.printf(
          "<tr><th>등록일</th> <td>%s</td></tr>\n",
          dateFormatter.format(board.getCreatedDate()));
      out.println("</table>");
      out.println("<div>");
      out.println("<button>변경</button>");
      out.println("<button type='reset'>초기화</button>");
      out.printf(
          "<a href='/board/delete?category=%d&no=%d'>삭제</a>\n",
          board.getCategory(),
          board.getNo());
      out.printf("<a href='/board/list?category=%d'>목록</a>\n", board.getCategory());
      out.println("</div>");
      out.println("</form>");



      try {
        board.setViewCount(board.getViewCount() + 1);
        boardDao.updateCount(board);
        sqlSessionFactory.openSession(false).commit();

      } catch (Exception e) {
        sqlSessionFactory.openSession(false).rollback();
      }

      out.println("</body>");
      out.println("</html>");
    }
  }

}


