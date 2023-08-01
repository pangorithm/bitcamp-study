package com.bitcamp.myapp.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.Component;

@Component("/schedule/delete")
public class ScheduleDeleteListener implements ScheduleActionListener {

  ScheduleDao scheduleDao;
  SqlSessionFactory sqlSessionFactory;

  public ScheduleDeleteListener(ScheduleDao scheduleDao, SqlSessionFactory sqlSessionFactory) {
    this.scheduleDao = scheduleDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    Schedule sch = scheduleDao.findBy(prompt.inputInt("번호? "));
    if (sch == null) {
      prompt.println("해당 번호의 스케줄이 없습니다!");
      return;
    }
    if (!sch.getOwner().equals(prompt.getAttribute("loginUser"))) {
      prompt.println("삭제 권한이 없습니다.");
      return;
    }
    ScheduleActionListener.printScheduleInfo(sch, prompt);
    if (prompt.inputString("정말로 이 스케줄을 삭제 하시겠습니까?(y/N)").equalsIgnoreCase("y")) {

      try {
        scheduleDao.delete(sch);
        sqlSessionFactory.openSession(false).commit();
      } catch (Exception e) {
        sqlSessionFactory.openSession(false).rollback();
      }
    }
  }

}
