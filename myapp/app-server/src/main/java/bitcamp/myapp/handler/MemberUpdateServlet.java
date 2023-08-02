package bitcamp.myapp.handler;

import java.io.PrintWriter;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;
import bitcamp.util.Component;
import bitcamp.util.HttpServletRequest;
import bitcamp.util.HttpServletResponse;
import bitcamp.util.Servlet;

@Component("/member/update")
public class MemberUpdateServlet implements Servlet {
  MemberDao memberDao;
  SqlSessionFactory sqlSessionFactory;

  public MemberUpdateServlet(MemberDao memberDao, SqlSessionFactory sqlSessionFactory) {
    this.memberDao = memberDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int memberNo = Integer.parseInt(request.getParameter("no"));

    Member m = memberDao.findBy(memberNo);
    if (m == null) {
      System.out.println("해당 번호의 회원이 없습니다!");
      return;
    }

    m.setName(request.getParameter("username"));
    m.setEmail(request.getParameter("email"));
    m.setPassword(request.getParameter("password"));
    m.setGender(request.getParameter("gender").charAt(0));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='1;url=/member/list'>");
    out.println("<title>멤버</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>멤버 변경</h1>");

    try {
      if (memberDao.update(m) == 0) {
        out.println("멤버가 없거나 변경 권한이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        out.println("변경했습니다!");
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      out.println("멤버 변경 실패입니다!");
      e.printStackTrace();
    }

    out.println("</body>");
    out.println("</html>");
  }


}
