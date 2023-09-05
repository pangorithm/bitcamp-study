package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.MemberService;
import com.bitcamp.myapp.vo.Member;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("/member/delete")
public class MemberDeleteController {

  @Autowired
  MemberService memberService;

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/login";
    }

    Member member = memberService.get(Integer.parseInt(request.getParameter("no")));
    if (member == null || member.getNo() != loginUser.getNo()) {
      throw new Exception("해당 번호의 멤버가 없거나 삭제 권한이 없습니다.");
    }

    try {
      memberService.delete(member.getNo());
      return "redirect:list";
    } catch (Exception e) {
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=list");
      throw e;

    }
  }

}
