package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.util.NcpObjectStorageService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;
import javax.servlet.http.Part;
import org.apache.ibatis.session.SqlSessionFactory;

@WebServlet("/member/update")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class MemberUpdateServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");
    MemberDao memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");
    NcpObjectStorageService ncpObjectStorageService = (NcpObjectStorageService) this.getServletContext()
        .getAttribute("ncpObjectStorageService");

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    int memberNo = Integer.parseInt(request.getParameter("no"));
    String newPassword = request.getParameter("password");

    Member m = memberDao.findBy(memberNo);
    if (m == null) {
      System.out.println("해당 번호의 회원이 없습니다!");
      return;
    }

    m.setName(request.getParameter("username").replaceAll("<script", "<scr!pt"));
    m.setEmail(request.getParameter("email").replaceAll("<script", "<scr!pt"));
    m.setTel(request.getParameter("tel").replaceAll("<script", "<scr!pt"));
    m.setPassword(newPassword);
    m.setGender(request.getParameter("gender").charAt(0));

    Part photoPart = request.getPart("photo");

    if (photoPart.getSize() > 0) {
      String uploadFileUrl =
          ncpObjectStorageService
              .uploadFile("bitcamp-nc7-bucket-14", "member/", photoPart);
      m.setPhoto(uploadFileUrl);
    }

    try {
      if (!m.equals(loginUser)) {
        throw new Exception("로그인한 계정만 수정 가능합니다.");
      } else if (memberDao.update(m) == 0) {
        throw new Exception("회원이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list");
      }

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();

      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=list");

      request.getRequestDispatcher("/error").forward(request, response);
    }

  }


}
