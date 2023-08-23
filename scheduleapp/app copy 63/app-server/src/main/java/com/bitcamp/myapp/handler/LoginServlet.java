package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.util.NcpObjectStorageService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;
import org.apache.ibatis.session.SqlSessionFactory;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    MemberDao memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");

    Member m = new Member();
    m.setEmail(request.getParameter("email"));
    m.setPassword(request.getParameter("password"));

    if (request.getParameter("saveEmail") != null) {
      Cookie cookie = new Cookie("email", m.getEmail());
      response.addCookie(cookie);
    } else {
      Cookie cookie = new Cookie("email", "no");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }

    Member loginUser = memberDao.findByEmailAndPassword(m);

    if (loginUser != null) {
      // 로그인 정보를 다른 요청에서도 사용할 수 있도록 세션 보관소에 담아 둔다.
      request.getSession().setAttribute("loginUser", loginUser);
      response.sendRedirect("/");
      // out.println("<meta http-eqiuv='refresh' content='1;url=/'>");
      return;
    }

    request.setAttribute("message", "회원 정보가 일치하지 않습니다.");
    request.setAttribute("refresh", "1;url=/auth/form");

    request.getRequestDispatcher("/error").forward(request, response);
  }


}
