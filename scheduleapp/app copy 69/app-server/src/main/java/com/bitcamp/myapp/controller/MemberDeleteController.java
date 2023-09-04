package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.MemberDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component("/member/delete")
public class MemberDeleteController implements PageController {

  MemberDao memberDao;
  PlatformTransactionManager txManager;

  public MemberDeleteController(MemberDao memberDao, PlatformTransactionManager txManager) {
    this.memberDao = memberDao;
    this.txManager = txManager;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      if (memberDao.delete(no) == 0) {
        throw new Exception("해당 번호의 멤버가 없거나 삭제 권한이 없습니다.");
      } else {
        txManager.commit(status);
        return "redirect:list";
      }
    } catch (Exception e) {
      txManager.rollback(status);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }
  }

}
