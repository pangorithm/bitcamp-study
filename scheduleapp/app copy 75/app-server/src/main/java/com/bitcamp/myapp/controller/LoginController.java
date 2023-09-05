package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.MemberService;
import com.bitcamp.myapp.vo.Member;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("/auth/login")
public class LoginController {

  @Autowired
  MemberService memberService;

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      return "/WEB-INF/jsp/auth/form.jsp";
    }

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

    Member loginUser = memberService.get(m.getEmail(), m.getPassword());
    if (loginUser == null) {
      request.setAttribute("refresh", "2;url=/app/auth/login");
      throw new Exception("회원 정보가 일치하지 않습니다.");
    }

    request.getSession().setAttribute("loginUser", loginUser);
    return "redirect:/";
  }


}
