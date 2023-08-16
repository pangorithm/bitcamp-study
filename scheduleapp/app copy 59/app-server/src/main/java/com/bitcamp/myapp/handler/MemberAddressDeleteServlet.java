package com.bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;

@WebServlet("/member/address/delete")
public class MemberAddressDeleteServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    int mano = Integer.parseInt((String) request.getParameter("mano"));
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.printf("<meta http-equiv='refresh' content='1;url=/member/detail?no=%d'>\n", memberNo);
    out.println("<title>주소</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>주소 삭제</h1>");

    try {
      InitServlet.memberDao.deleteMemberAddress(loginUser.getNo(), mano);
      InitServlet.sqlSessionFactory.openSession(false).commit();
      out.println("<p>삭제 성공입니다</p>");
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      out.println("<p>삭제 실패입니다</p>");
      e.printStackTrace();
    }

    out.println("</body>");
    out.println("</html>");
  }

}
