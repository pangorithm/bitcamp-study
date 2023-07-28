package com.bitcamp.myapp.handler;

import java.io.IOException;
import java.util.List;
import org.apache.ibatis.session.SqlSessionFactory;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;

public class ScheduleDetailListener implements ActionListener {

  ScheduleDao scheduleDao;
  SqlSessionFactory sqlSessionFactory;

  public ScheduleDetailListener(ScheduleDao scheduleDao, SqlSessionFactory sqlSessionFactory) {
    this.scheduleDao = scheduleDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    int scheduleNo = prompt.inputInt("번호? ");

    Schedule schedule = scheduleDao.findBy(scheduleNo, (Member) prompt.getAttribute("loginUser"));
    if (schedule == null) {
      prompt.println("해당 번호의 스케줄이 없습니다!");
      return;
    }

    prompt.printf("번호: %d\n", schedule.getNo());
    prompt.printf("제목: %s\n", schedule.getScheduleTitle());
    prompt.printf("시작 시간: %s\n", schedule.getStartTime().toString());
    prompt.printf("종료 시간: %s\n", schedule.getEndTime().toString());
    prompt.printf("소유자: %s\n", schedule.getOwner().getName());

    List<Member> participantList = scheduleDao.findAllParticipatedMember(schedule.getNo());
    prompt.printf("스케줄 참가자 명단\n");
    prompt.printf(" 번호 | 이름\n");
    for (Member m : participantList.toArray(new Member[0])) {
      prompt.printf(" %-4d | %s\n", m.getNo(), m.getName());
    }
    prompt.println("");
    prompt.writeBuf();
    if (schedule.getOwner().equals(prompt.getAttribute("loginUser"))) {
      while (prompt.promptContinue(prompt.inputString("스케줄 참가자를 추가하시겠습니까?(y/N)"))) {

        try {
          int result =
              scheduleDao.scheduleAddParticipant(schedule.getNo(), prompt.inputInt("추가할 참가자 번호?"));
          if (result == -1) {
            prompt.println("이미 참가중인 멤버입니다.");
            prompt.end();
          } else if (result == -2) {
            prompt.println("존재하지 않는 멤버입니다.");
            prompt.end();
          }

          sqlSessionFactory.openSession(false).commit();
        } catch (Exception e) {
          sqlSessionFactory.openSession(false).rollback();
        }
      }
    }


  }
}


