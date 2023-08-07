package bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.AbstractServlet;

@WebServlet("/board/update")
public class BoardUpdateServlet extends AbstractServlet {

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    int category = Integer.parseInt((String) request.getParameter("category"));
    Board board = new Board();
    board.setNo(Integer.parseInt(request.getParameter("no")));
    board.setTitle(request.getParameter("title"));
    board.setContent(request.getParameter("content"));
    board.setWriter(loginUser);
    board.setCategory(category);

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
      if (InitServlet.boardDao.update(board) == 0) {
        out.println("게시글이 없거나 변경 권한이 없습니다.");
      } else {
        InitServlet.sqlSessionFactory.openSession(false).commit();
        out.println("변경했습니다!");
      }
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      out.println("게시글 변경 실패입니다!");
      e.printStackTrace();
    }
    out.println("</body>");
    out.println("</html>");
  }

}


