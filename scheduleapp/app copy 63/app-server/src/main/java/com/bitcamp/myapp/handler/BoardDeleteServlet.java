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
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.myapp.vo.Member;
import org.apache.ibatis.session.SqlSessionFactory;

@WebServlet("/board/delete")
public class BoardDeleteServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");
    BoardDao boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    int category = Integer.parseInt((String) request.getParameter("category"));

    Board b = new Board();
    b.setNo(Integer.parseInt(request.getParameter("no")));
    b.setWriter(loginUser);
    b.setCategory(category);

    try {
      if (boardDao.delete(b) == 0) {
        throw new Exception("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("/board/list?category=" + category);
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();

      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=list?category=" + request.getParameter("category"));

      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}


