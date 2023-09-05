package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.MemberService;
import com.bitcamp.myapp.service.NcpObjectStorageService;
import com.bitcamp.myapp.vo.AddressType;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.MemberAddress;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

  @Autowired
  MemberService memberService;
  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @RequestMapping("/member/add")
  public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      return "/WEB-INF/jsp/member/form.jsp";
    }

    Member member = new Member();
    member.setName(request.getParameter("name"));
    member.setEmail(request.getParameter("email"));
    member.setPassword(request.getParameter("password"));
    member.setGender(request.getParameter("gender").charAt(0));
    member.setTel(request.getParameter("tel"));

    Part photoPart = request.getPart("photo");
    if (photoPart.getSize() > 0) {
      String uploadFileUrl =
          ncpObjectStorageService
              .uploadFile("bitcamp-nc7-bucket-14", "member/", photoPart);
      member.setPhoto(uploadFileUrl);
    }

    try {
      memberService.add(member);
      return "redirect:list";

    } catch (Exception e) {
      request.setAttribute("message", "회원 등록 오류!");
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }
  }

  @RequestMapping("/member/delete")
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

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

  @RequestMapping("/member/detail")
  public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Member m = memberService.get(Integer.parseInt(request.getParameter("no")));
    request.setAttribute("m", m);
    request.setAttribute("addressList", m.getAddressList());
    List<AddressType> addressTypeList = memberService.getAddressTypeList();
    request.setAttribute("addressTypeList", addressTypeList);
    return "/WEB-INF/jsp/member/detail.jsp";
  }

  @RequestMapping("/member/list")
  public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setAttribute("list", memberService.list());
    return "/WEB-INF/jsp/member/list.jsp";
  }

  @RequestMapping("/member/update")
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    int memberNo = Integer.parseInt(request.getParameter("no"));
    String newPassword = request.getParameter("password");

    Member m = memberService.get(memberNo);
    if (m == null || m.getNo() != loginUser.getNo()) {
      throw new Exception("해당 번호의 회원이 없거나 권한이 없습니다!");
    }

    m.setName(request.getParameter("username"));
    m.setEmail(request.getParameter("email"));
    m.setTel(request.getParameter("tel"));
    m.setPassword(newPassword);
    m.setGender(request.getParameter("gender").charAt(0));

    Part photoPart = request.getPart("photo");

    if (photoPart.getSize() > 0) {
      String uploadFileUrl =
          ncpObjectStorageService
              .uploadFile("bitcamp-nc7-bucket-14", "member/", photoPart);
      m.setPhoto(uploadFileUrl);
    }

    try {
      memberService.update(m);
      return "redirect:list";

    } catch (Exception e) {
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=list");

      throw e;
    }

  }

  @RequestMapping("/member/addressAdd")
  public String addressAdd(HttpServletRequest request, HttpServletResponse response)
      throws Exception {

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

  @RequestMapping("/member/addressDelete")
  public String addressDelete(HttpServletRequest request, HttpServletResponse response)
      throws Exception {

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
