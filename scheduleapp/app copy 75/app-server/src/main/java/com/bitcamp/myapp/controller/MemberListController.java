package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("/member/list")
public class MemberListController {

  @Autowired
  MemberService memberService;

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setAttribute("list", memberService.list());
    return "/WEB-INF/jsp/member/list.jsp";
  }

}