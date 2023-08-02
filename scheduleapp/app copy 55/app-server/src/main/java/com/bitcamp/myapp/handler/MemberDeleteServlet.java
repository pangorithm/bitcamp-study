package com.bitcamp.myapp.handler;

import org.apache.ibatis.session.SqlSessionFactory;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.util.Component;
import com.bitcamp.util.HttpServletRequest;
import com.bitcamp.util.HttpServletResponse;
import com.bitcamp.util.Servlet;

@Component("/member/delete")
public class MemberDeleteServlet implements Servlet {
  MemberDao memberDao;
  SqlSessionFactory sqlSessionFactory;

  public MemberDeleteServlet(MemberDao memberDao, SqlSessionFactory sqlSessionFactory) {

    this.memberDao = memberDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    try {
      if (memberDao.delete(no) == 0) {
        throw new Exception("해당 번호의 멤버가 없거나 삭제 권한이 없습니다.");
      } else {
        response.sendRedirect("/member/list");
      }
      sqlSessionFactory.openSession(false).commit();
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }

}
