package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.util.NcpObjectStorageService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;
import javax.servlet.http.Part;
import org.apache.ibatis.session.SqlSessionFactory;

@WebServlet("/member/add")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class MemberAddController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    request.getRequestDispatcher("/WEB-INF/jsp/member/form.jsp").include(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");
    MemberDao memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");
    NcpObjectStorageService ncpObjectStorageService = (NcpObjectStorageService) this.getServletContext()
        .getAttribute("ncpObjectStorageService");

    Member member = new Member();
    member.setName(request.getParameter("name").replaceAll("<script", "<scr!pt"));
    member.setEmail(request.getParameter("email").replaceAll("<script", "<scr!pt"));
    member.setPassword(request.getParameter("password").replaceAll("<script", "<scr!pt"));
    member.setGender(request.getParameter("gender").charAt(0));
    member.setTel(request.getParameter("tel"));

    Part photoPart = request.getPart("photo");
    if (photoPart.getSize() > 0) {
      String uploadFileUrl =
          ncpObjectStorageService
              .uploadFile("bitcamp-nc7-bucket-14", "member/", photoPart);
      member.setPhoto(uploadFileUrl);
    }

    try {
      memberDao.insert(member);
      sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("list");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("message", "회원 등록 오류!");
      request.setAttribute("refresh", "2;url=list");
      throw new ServletException(e);
    }

  }

}
