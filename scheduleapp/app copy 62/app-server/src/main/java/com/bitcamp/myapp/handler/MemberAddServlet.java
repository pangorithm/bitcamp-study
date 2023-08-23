package com.bitcamp.myapp.handler;

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

@WebServlet("/member/add")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class MemberAddServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member member = new Member();
    member.setName(request.getParameter("name").replaceAll("<script", "<scr!pt"));
    member.setEmail(request.getParameter("email").replaceAll("<script", "<scr!pt"));
    member.setPassword(request.getParameter("password").replaceAll("<script", "<scr!pt"));
    member.setGender(request.getParameter("gender").charAt(0));
    member.setTel(request.getParameter("tel"));

    Part photoPart = request.getPart("photo");
    if (photoPart.getSize() > 0) {
      String uploadFileUrl =
          InitServlet.ncpObjectStorageService
              .uploadFile("bitcamp-nc7-bucket-14", "member/", photoPart);
      member.setPhoto(uploadFileUrl);
    }

    try {
      InitServlet.memberDao.insert(member);
      InitServlet.sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("list");
      
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();

      request.setAttribute("error", e);
      request.setAttribute("message", "회원 등록 오류!");
      request.setAttribute("refresh", "2;url=list");

      request.getRequestDispatcher("/error").forward(request, response);
    }

  }

}
