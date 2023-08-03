package com.bitcamp.myapp.handler;

import org.apache.ibatis.session.SqlSessionFactory;
import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.util.Component;
import com.bitcamp.util.HttpServletRequest;
import com.bitcamp.util.HttpServletResponse;
import com.bitcamp.util.Servlet;

@Component("/board/delete")
public class BoardDeleteServlet implements Servlet {

  BoardDao boardDao;
  SqlSessionFactory sqlSessionFactory;

  public BoardDeleteServlet(BoardDao boardDao, SqlSessionFactory sqlSessionFactory) {
    this.boardDao = boardDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {

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
        response.sendRedirect("/board/list?category=" + category);
      }
      sqlSessionFactory.openSession(false).commit();
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }
}


