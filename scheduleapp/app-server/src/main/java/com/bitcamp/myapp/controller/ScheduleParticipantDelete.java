package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.ScheduleDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component("/schedule/participantDelete")
public class ScheduleParticipantDelete implements PageController {

  ScheduleDao scheduleDao;
  PlatformTransactionManager txManager;

  public ScheduleParticipantDelete(ScheduleDao scheduleDao, PlatformTransactionManager txManager) {
    this.scheduleDao = scheduleDao;
    this.txManager = txManager;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int scheduleNo = Integer.parseInt(request.getParameter("no"));
    int deleteParticipantNo = Integer.parseInt(request.getParameter("deleteParticipantNo"));

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      int result = scheduleDao.deleteScheduleParticipant(scheduleNo, deleteParticipantNo);

      if (result == -1) {
        throw new Exception("참여하지 않는 멤버입니다.");
      } else if (result == -2) {
        throw new Exception("존재하지 않는 멤버입니다.");
      } else {
        txManager.commit(status);
        return "redirect:detail?no=" + scheduleNo;
      }

    } catch (Exception e) {
      txManager.rollback(status);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=detail?no=" + scheduleNo);
      throw e;
    }
  }

}
