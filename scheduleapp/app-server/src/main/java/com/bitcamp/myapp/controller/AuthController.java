package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.MemberService;
import com.bitcamp.myapp.vo.Member;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {

  @Autowired
  MemberService memberService;

  @RequestMapping("/auth/form")
  public String form() {
    return "/WEB-INF/jsp/auth/form.jsp";
  }

  @RequestMapping("/auth/login")
  public String login(
      String email,
      String password,
      String saveEmail,
      HttpSession session,
      Map<String, Object> model,
      HttpServletResponse response) throws Exception {

    if (saveEmail != null) {
      Cookie cookie = new Cookie("email", email);
      response.addCookie(cookie);
    } else {
      Cookie cookie = new Cookie("email", "no");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }

    Member loginUser = memberService.get(email, password);
    if (loginUser == null) {
      model.put("refresh", "2;url=form");
      throw new Exception("회원 정보가 일치하지 않습니다.");
    }

    session.setAttribute("loginUser", loginUser);
    return "redirect:/";
  }

  @RequestMapping("/auth/logout")
  public String logout(HttpSession session) throws Exception {
    session.invalidate();
    return "redirect:/";
  }
}
