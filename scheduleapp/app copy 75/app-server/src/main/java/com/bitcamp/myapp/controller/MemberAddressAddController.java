package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.MemberService;
import com.bitcamp.myapp.vo.AddressType;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.MemberAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("/member/addressAdd")
public class MemberAddressAddController {

  @Autowired
  MemberService memberService;

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    MemberAddress memberAddress = new MemberAddress();
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));
    AddressType addressType = new AddressType();

    memberAddress.setMno(memberNo);
    addressType.setNo(Integer.parseInt(request.getParameter("addressType")));
    memberAddress.setAddressType(addressType);
    memberAddress.setPostAddress(request.getParameter("postAddress"));
    memberAddress.setBasicAddress(request.getParameter("basicAddr"));
    memberAddress.setDetailAddress(request.getParameter("detailAddr"));

    try {
      if (memberNo == loginUser.getNo()) {
        memberService.addAddress(memberAddress);
        return "redirect:detail?no=" + memberNo;
      } else {
        throw new Exception("로그인한 계정만 주소 추가 가능합니다");
      }
    } catch (Exception e) {
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=/member/detail?no=" + memberNo);
      throw e;
    }
  }
}
