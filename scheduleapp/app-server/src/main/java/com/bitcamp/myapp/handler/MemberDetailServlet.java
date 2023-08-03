package com.bitcamp.myapp.handler;

import java.io.PrintWriter;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.util.Component;
import com.bitcamp.util.HttpServletRequest;
import com.bitcamp.util.HttpServletResponse;
import com.bitcamp.util.Servlet;

@Component("/member/detail")
public class MemberDetailServlet implements Servlet {

  MemberDao memberDao;

  public MemberDetailServlet(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Member m = memberDao.findBy(Integer.parseInt(request.getParameter("no")));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>회원</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원</h1>");

    if (m == null) {
      out.println("해당 번호의 회원이 없습니다!");
    } else {

      out.println("<form action='/member/update' method='post'>");
      out.println("<table border='1'>");

      out.printf(
        "<tr><th style='width:200px;'>번호</th> "
            + "<td style='width:300px;'><input type='text' name='no' value='%d' readonly='readonly'></td></tr>\n",
        m.getNo());
      out.printf(
        "<tr><th>이름</th><td><input type='text' name='username' value='%s'></td></tr>\n",
        m.getName());
      out.printf(
        "<tr><th>이메일</th> <td><input type='text' name='email' value='%s'></td></tr>\n",
        m.getEmail());
      out.printf(
        "<tr><th>성별(남자:M/여자:W)</th> <td><input type='text' name='gender' value='%s' maxlength='1'></td></tr>\n",
        String.valueOf(m.getGender()));
      out.println("<tr><th>비밀번호</th> <td><input type='text' name='password' value=''></td></tr>\n");

      out.println("</table>");
      out.println("<div>");
      out.println("<button>변경</button>");
      out.println("<button type='reset'>초기화</button>");
      out.printf("<a href='/member/delete?no=%d&password=%s'>삭제</a>\n", m.getNo(), m.getPassword());
      out.println("<a href='/member/list'>목록</a>\n");
      out.println("</div>");
      out.println("</form>");
    }

    out.println("</body>");
    out.println("</html>");
  }

}
