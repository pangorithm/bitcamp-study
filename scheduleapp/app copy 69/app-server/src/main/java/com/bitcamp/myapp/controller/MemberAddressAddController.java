package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.vo.AddressType;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.MemberAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component("/member/addressAdd")
public class MemberAddressAddController implements PageController {

  MemberDao memberDao;
  PlatformTransactionManager txManager;

  public MemberAddressAddController(MemberDao memberDao, PlatformTransactionManager txManager) {
    this.memberDao = memberDao;
    this.txManager = txManager;
  }

  @Override
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

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      if (memberNo == loginUser.getNo()) {
        memberDao.insertMemberAddress(memberAddress);
        txManager.commit(status);
        return "redirect:detail?no=" + memberNo;
      } else {
        throw new Exception("로그인한 계정만 주소 추가 가능합니다");
      }
    } catch (Exception e) {
      txManager.rollback(status);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=/member/detail?no=" + memberNo);
      throw e;
    }
  }
}
