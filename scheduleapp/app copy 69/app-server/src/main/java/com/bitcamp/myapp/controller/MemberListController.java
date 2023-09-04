package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.MemberDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;
import org.springframework.stereotype.Component;

@Component("/member/list")
public class MemberListController implements PageController {

  MemberDao memberDao;

  public MemberListController(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setAttribute("list", memberDao.findAll());
    return "/WEB-INF/jsp/member/list.jsp";
  }

}