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
import com.bitcamp.myapp.vo.MemberAddress;

@WebServlet("/member/detail")
public class MemberDetailServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    Member m = InitServlet.memberDao.findBy(Integer.parseInt(request.getParameter("no")));

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

      out
          .printf(
              "<tr><th style='width:200px;'>번호</th> "
                  + "<td style='width:300px;'><input type='text' name='no' value='%d' readonly='readonly'></td></tr>\n",
              m.getNo());
      out
          .printf(
              "<tr><th>이름</th><td><input type='text' name='username' value='%s'></td></tr>\n",
              m.getName());
      out
          .printf(
              "<tr><th>이메일</th> <td><input type='text' name='email' value='%s'></td></tr>\n",
              m.getEmail());
      out
          .printf(
              "<tr><th>전화번호</th> <td><input type='text' name='tel' value='%s'></td></tr>\n",
              m.getTel());
      out
          .printf(
              "<tr><th>성별</th>\n"
                  + " <td><select name='gender'>\n"
                  + " <option value='M' %s>남자</option>\n"
                  + " <option value='W' %s>여자</option></select></td></tr>\n",
              (m.getGender() == 'M' ? "selected" : ""),
              (m.getGender() == 'W' ? "selected" : ""));
      out.println("<tr><th>비밀번호</th> <td><input type='text' name='password' value=''></td></tr>\n");
      out.println("</table>");


      out.println("<div>");
      out.println("<button type='reset'>초기화</button>");
      if (loginUser.equals(m)) {
        out.println("<button>변경</button>");
        out
            .printf(
                "<a href='/member/delete?no=%d&password=%s'>삭제</a>\n",
                m.getNo(),
                m.getPassword());
      }
      out.println("<a href='/member/list'>목록</a>\n");
      out.println("</div>");
      out.println("</form>");
    }

    out.println("<div>");
    out.println("<table border='1'>");
    if (loginUser.equals(m)) {
      List<MemberAddress> adressList = InitServlet.memberDao.getMembersAddressList(m.getNo());
      out.println("<tr> <th>유형</th> <th>우편번호</th> <th>기본주소</th> <th>상세주소</th></tr>");
      for (MemberAddress maddr : adressList) {
        out.println("<form action='/member/몰루?' method='post'>");
        out
            .printf(
                "<tr> <td>%s</td> <td>%s</td> <td>%s</td> <td>%s</td> "
                    + "<td><button>삭제</button></td>\n",
                maddr.getAddressType(),
                maddr.getPostAddress(),
                maddr.getBasicAddress(),
                maddr.getDetailAddress());
        out.println("</form>");
      }
    }
    out.println("</table>");
    out.println("<form action='/member/몰루?' method='post'>");
    out.println("우편번호 <input type='text' name='postAddress'><br>");
    out.println("</form>");
    out.println("</div>");

    out.println("</body>");
    out.println("</html>");
  }

}
