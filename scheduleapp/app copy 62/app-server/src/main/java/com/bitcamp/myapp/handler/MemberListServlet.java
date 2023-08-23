package com.bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;

@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>회원</title>");
    out.println("</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);

    out.println("<h1>회원 목록 </h1>");
    out.println("<div style='margin:5px;'>");
    out.println("<a href='/member/form.html'>새 회원</a>\n");
    out.println("</div>");
    out.println("<table border='1'>");
    out.println("<thead>");
    out.println("<tr><th>번호</th> <th>이름</th> <th>이메일</th>");
    out.println("</thead>");
    out.println("<tbody>");

    // 목록에서 데이터를 대신 꺼내주는 객체를 얻는다.
    List<Member> list = InitServlet.memberDao.findAll();

    for (Member m : list) {
      out
          .printf(
              "<tr>"
                  + "<td>%d</td> "
                  + "<td>"
                  + "<img src='http://gjoxpfbmymto19010706.cdn.ntruss.com/%s?type=f&w=30&h=40&faceopt=true&ttype=jpg'>"
                  + "<a href='/member/detail?no=%d'>%s</a>"
                  + "</td>"
                  + "<td>%s</td>"
                  + "</tr>\n",
              m.getNo(),
              m.getPhoto() == null ? "icon/avatar.png" : "member/" + m.getPhoto(),
              m.getNo(),
              m.getName().matches("^\\s+$") ? "이름없음" : m.getName(),
              m.getEmail());
    }

    out.println("</tbody>");
    out.println("</table>");
    out.println("<a href='/'>메인</a>");

    request.getRequestDispatcher("/footer").include(request, response);

    out.println("</body>");
    out.println("</html>");
  }
}
