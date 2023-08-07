package bitcamp.myapp.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.util.AbstractServlet;

@WebServlet("/member/delete")
public class MemberDeleteServlet extends AbstractServlet {

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    int no = Integer.parseInt(request.getParameter("no"));
    try {
      if (InitServlet.memberDao.delete(no) == 0) {
        throw new Exception("해당 번호의 멤버가 없거나 삭제 권한이 없습니다.");
      } else {
        response.sendRedirect("/member/list");
      }
      InitServlet.sqlSessionFactory.openSession(false).commit();
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }

}
