package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.util.NcpObjectStorageService;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Board;
import org.apache.ibatis.session.SqlSessionFactory;

@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    BoardDao boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    int category = Integer.parseInt(request.getParameter("category"));

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>게시글</title>");
    out.println("</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);

    out.println("<h1>게시글 목록 </h1>");
    out.println("<div style='margin:5px;'>");
    out.printf("<a href='/board/form?category=%d'>새 글</a>\n", category);
    out.println("</div>");
    out.println("<table border='1'>");
    out.println("<thead>");
    out
        .println(
            "<tr>"
                + "<th>번호</th> <th>제목</th> <th>작성자</th> <th>조회수</th> <th>등록일</th> <th>수정일</th>"
                + "</tr>");
    out.println("</thead>");

    out.println("<tbody>");
    List<Board> list = boardDao.findAll(category);
    for (Board board : list) {
      out
          .printf(
              "<tr><td>%d</td> <td><a href='/board/detail?category=%d&no=%d'>%s</a>"
                  + "</td> <td>%s</td> <td>%d</td> <td>%s</td> <td>%s</td></tr>\n",
              board.getNo(),
              board.getCategory(),
              board.getNo(),
              board.getTitle().matches("^\\s+$") ? "제목없음" : board.getTitle(),
              board.getWriter().getName(),
              board.getViewCount(),
              dateFormatter.format(board.getCreatedAt()),
              board.getUpdatedAt() == null ? "-" : dateFormatter.format(board.getUpdatedAt()));
    }
    out.println("</tbody>");
    out.println("</table>");
    out.println("<a href='/'>메인</a>");

    request.getRequestDispatcher("/footer").include(request, response);

    out.println("</body>");
    out.println("</html>");
  }

}


