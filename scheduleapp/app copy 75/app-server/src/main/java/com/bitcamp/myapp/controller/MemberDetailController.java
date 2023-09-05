package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.MemberService;
import com.bitcamp.myapp.vo.AddressType;
import com.bitcamp.myapp.vo.Member;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller("/member/detail")
public class MemberDetailController {

  @Autowired
  MemberService memberService;

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Member m = memberService.get(Integer.parseInt(request.getParameter("no")));
    request.setAttribute("m", m);
    request.setAttribute("addressList", m.getAddressList());
    List<AddressType> addressTypeList = memberService.getAddressTypeList();
    request.setAttribute("addressTypeList", addressTypeList);
    return "/WEB-INF/jsp/member/detail.jsp";
  }
}