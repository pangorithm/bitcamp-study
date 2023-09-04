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
import com.bitcamp.myapp.vo.AddressType;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.MemberAddress;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;


@Component("/member/detail")
public class MemberDetailController implements PageController {

  MemberDao memberDao;

  public MemberDetailController(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Member m = memberDao.findBy(Integer.parseInt(request.getParameter("no")));
    request.setAttribute("m", m);
    List<MemberAddress> addressList = memberDao.getMembersAddressList(m.getNo());
    request.setAttribute("addressList", addressList);
    List<AddressType> addressTypeList = memberDao.findAllAddressType();
    request.setAttribute("addressTypeList", addressTypeList);
    return "/WEB-INF/jsp/member/detail.jsp";
  }
}