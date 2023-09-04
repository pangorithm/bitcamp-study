package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component("/schedule/delete")
public class ScheduleDeleteController implements PageController {

  ScheduleDao scheduleDao;
  PlatformTransactionManager txManager;

  public ScheduleDeleteController(ScheduleDao scheduleDao, PlatformTransactionManager txManager) {
    this.scheduleDao = scheduleDao;
    this.txManager = txManager;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Schedule sch = scheduleDao.findBy(Integer.parseInt(request.getParameter("no")));
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      if (sch == null) {
        throw new Exception("해당 번호의 스케줄이 없습니다!");
      }

      if (!sch.getOwner().equals(loginUser)) {
        throw new Exception("삭제 권한이 없습니다.");
      }

      scheduleDao.deleteAllScheduleParticipant(sch.getNo());
      scheduleDao.delete(sch);
      txManager.commit(status);
      return "redirect:list";
    } catch (Exception e) {
      txManager.rollback(status);
      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }

  }

}
