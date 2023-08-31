package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.service.NcpObjectStorageService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;
import javax.servlet.http.Part;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

@Component("/member/update")
public class MemberUpdateController implements PageController {

  MemberDao memberDao;
  SqlSessionFactory sqlSessionFactory;
  NcpObjectStorageService ncpObjectStorageService;

  public MemberUpdateController(
      MemberDao memberDao,
      SqlSessionFactory sqlSessionFactory,
      NcpObjectStorageService ncpObjectStorageService) {
    this.memberDao = memberDao;
    this.sqlSessionFactory = sqlSessionFactory;
    this.ncpObjectStorageService = ncpObjectStorageService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    int memberNo = Integer.parseInt(request.getParameter("no"));
    String newPassword = request.getParameter("password");

    Member m = memberDao.findBy(memberNo);
    if (m == null) {
      throw new Exception("해당 번호의 회원이 없습니다!");
    }

    m.setName(request.getParameter("username"));
    m.setEmail(request.getParameter("email"));
    m.setTel(request.getParameter("tel"));
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
        return "redirect:list";
      }

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();

      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=list");

      throw e;
    }

  }


}
