package com.bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

  @RequestMapping("/")
  public String home(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/WEB-INF/jsp/index.jsp";
  }
}
