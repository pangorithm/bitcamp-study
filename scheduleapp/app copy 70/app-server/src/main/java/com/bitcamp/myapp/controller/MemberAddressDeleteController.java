package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.MemberService;
import com.bitcamp.myapp.vo.Member;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("/member/addressDelete")
public class MemberAddressDeleteController implements PageController {

  @Autowired
  MemberService memberService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    int mano = Integer.parseInt((String) request.getParameter("mano"));
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));

    try {
      memberService.deleteAddress(mano);
      return "redirect:detail?no=" + memberNo;
    } catch (Exception e) {
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=/member/detail?no=" + memberNo);
      throw e;
    }
  }

}
