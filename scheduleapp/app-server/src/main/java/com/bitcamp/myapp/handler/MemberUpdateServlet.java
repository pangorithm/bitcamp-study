package com.bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;

@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    int memberNo = Integer.parseInt(request.getParameter("no"));

    Member m = InitServlet.memberDao.findBy(memberNo);
    if (m == null) {
      System.out.println("해당 번호의 회원이 없습니다!");
      return;
    }

    m.setName(request.getParameter("username").replaceAll("<script", "<s c r i p t"));
    m.setEmail(request.getParameter("email").replaceAll("<script", "<s c r i p t"));
    m.setPassword(request.getParameter("password").replaceAll("<script", "<s c r i p t"));
    m.setGender(request.getParameter("gender").charAt(0));

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='1;url=/member/list'>");
    out.println("<title>회원</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원 변경</h1>");

    try {
      if (memberNo != loginUser.getNo()) {
        out.println("본인 계정만 수정 가능합니다.");
      } else if (InitServlet.memberDao.update(m) == 0) {
        out.println("해당 회원이 없습니다나.");
      } else {
        InitServlet.sqlSessionFactory.openSession(false).commit();
        out.println("변경했습니다!");
      }
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      out.println("회원 변경 실패입니다!");
      e.printStackTrace();
    }

    out.println("</body>");
    out.println("</html>");
  }


}
