package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.MemberService;
import com.bitcamp.myapp.service.NcpObjectStorageService;
import com.bitcamp.myapp.vo.AddressType;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.MemberAddress;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

  @Autowired
  MemberService memberService;
  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @RequestMapping("/member/form")
  public String form() {
    return "/WEB-INF/jsp/member/form.jsp";
  }

  @RequestMapping("/member/add")
  public String add(
      Member member,
      @RequestParam("photofile") Part photofile,
      Map<String, Object> model) throws Exception {

    if (photofile.getSize() > 0) {
      String uploadFileUrl =
          ncpObjectStorageService
              .uploadFile("bitcamp-nc7-bucket-14", "member/", photofile);
      member.setPhoto(uploadFileUrl);
    }

    try {
      memberService.add(member);
      return "redirect:list";

    } catch (Exception e) {
      model.put("message", "회원 등록 오류!");
      model.put("refresh", "2;url=list");
      throw e;
    }
  }

  @RequestMapping("/member/delete")
  public String delete(
      @RequestParam("no") int no,
      Map<String, Object> model,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/login";
    }

    Member member = memberService.get(no);
    if (member == null || member.getNo() != loginUser.getNo()) {
      throw new Exception("해당 번호의 멤버가 없거나 삭제 권한이 없습니다.");
    }

    try {
      memberService.delete(member.getNo());
      return "redirect:list";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      model.put("refresh", "2;url=list");
      throw e;

    }
  }

  @RequestMapping("/member/detail")
  public String detail(@RequestParam("no") int no, Map<String, Object> model) throws Exception {
    Member m = memberService.get(no);
    model.put("m", m);
    model.put("addressList", m.getAddressList());
    List<AddressType> addressTypeList = memberService.getAddressTypeList();
    model.put("addressTypeList", addressTypeList);
    return "/WEB-INF/jsp/member/detail.jsp";
  }

  @RequestMapping("/member/list")
  public String list(Map<String, Object> model) throws Exception {
    model.put("list", memberService.list());
    return "/WEB-INF/jsp/member/list.jsp";
  }

  @RequestMapping("/member/update")
  public String update(
      Member member,
      @RequestParam("photofile") Part photofile,
      Map<String, Object> model,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    Member m = memberService.get(member.getNo());
    if (m == null || m.getNo() != loginUser.getNo()) {
      throw new Exception("해당 번호의 회원이 없거나 권한이 없습니다!");
    }

    if (photofile.getSize() > 0) {
      String uploadFileUrl =
          ncpObjectStorageService
              .uploadFile("bitcamp-nc7-bucket-14", "member/", photofile);
      m.setPhoto(uploadFileUrl);
    }

    try {
      memberService.update(member);
      return "redirect:list";

    } catch (Exception e) {
      model.put("message", e.getMessage());
      model.put("refresh", "2;url=list");

      throw e;
    }

  }

  @RequestMapping("/member/addressAdd")
  public String addressAdd(
      MemberAddress memberAddress,
      AddressType addressType,
      Map<String, Object> model,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");

    memberAddress.setAddressType(addressType);

    try {
      if (memberAddress.getMno() == loginUser.getNo()) {
        memberService.addAddress(memberAddress);
        return "redirect:detail?no=" + memberAddress.getMno();
      } else {
        throw new Exception("로그인한 계정만 주소 추가 가능합니다");
      }
    } catch (Exception e) {
      model.put("message", e.getMessage());
      model.put("refresh", "2;url=/member/detail?no=" + memberAddress.getMno());
      throw e;
    }
  }

  @RequestMapping("/member/addressDelete")
  public String addressDelete(
      MemberAddress memberAddress,
      Map<String, Object> model,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");

    try {
      if (memberAddress.getMno() == loginUser.getNo()) {
        memberService.deleteAddress(memberAddress.getNo());
        return "redirect:detail?no=" + memberAddress.getMno();
      } else {
        throw new Exception("로그인한 계정만 주소 추가 가능합니다");
      }
    } catch (Exception e) {
      model.put("message", e.getMessage());
      model.put("refresh", "2;url=/member/detail?no=" + memberAddress.getMno());
      throw e;
    }
  }
}
