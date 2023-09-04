package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component("/schedule/update")
public class ScheduleUpdateController implements PageController {

  ScheduleDao scheduleDao;
  PlatformTransactionManager txManager;

  public ScheduleUpdateController(ScheduleDao scheduleDao, PlatformTransactionManager txManager) {
    this.scheduleDao = scheduleDao;
    this.txManager = txManager;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Schedule sch = new Schedule();
    sch.setNo(Integer.parseInt(request.getParameter("no")));
    sch.setTitle(request.getParameter("title"));
    sch.setContent(request.getParameter("content"));
    sch.setStartTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("start-time"))));
    sch.setEndTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("end-time"))));
    sch.setOwner((Member) request.getSession().getAttribute("loginUser"));

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      if (scheduleDao.update(sch) == 0) {
        throw new Exception("스케줄이 없거나 변경 권한이 없습니다.");
      } else {
        txManager.commit(status);
        return "redirect:list";
      }
    } catch (Exception e) {
      txManager.rollback(status);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=detail?no=" + sch.getNo());
      throw e;
    }
  }
}


