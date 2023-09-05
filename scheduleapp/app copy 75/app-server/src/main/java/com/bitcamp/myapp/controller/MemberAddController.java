package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.MemberService;
import com.bitcamp.myapp.service.NcpObjectStorageService;
import com.bitcamp.myapp.vo.Member;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("/member/add")
public class MemberAddController {

  @Autowired
  MemberService memberService;
  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      return "/WEB-INF/jsp/member/form.jsp";
    }

    Member member = new Member();
    member.setName(request.getParameter("name"));
    member.setEmail(request.getParameter("email"));
    member.setPassword(request.getParameter("password"));
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
      memberService.add(member);
      return "redirect:list";

    } catch (Exception e) {
      request.setAttribute("message", "회원 등록 오류!");
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }

  }

}
