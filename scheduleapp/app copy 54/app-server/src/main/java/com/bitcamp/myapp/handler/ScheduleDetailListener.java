package com.bitcamp.myapp.handler;

import java.io.IOException;
import java.util.List;
import org.apache.ibatis.session.SqlSessionFactory;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.Component;

@Component("/schedule/detail")
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

    Schedule schedule = scheduleDao.findBy(scheduleNo);
    if (schedule == null) {
      prompt.println("해당 번호의 스케줄이 없습니다!");
      return;
    }

    prompt.printf("번호: %d\n", schedule.getNo());
    prompt.printf("제목: %s\n", schedule.getScheduleTitle());
    prompt.printf("시작 시간: %s\n", schedule.getStartTime().toString());
    prompt.printf("종료 시간: %s\n", schedule.getEndTime().toString());
    prompt.printf("소유자: %s\n", schedule.getOwner().getName());

    if (schedule.getOwner().equals(prompt.getAttribute("loginUser"))) {
      int selectNo = 0;
      do {
        printParticipants(schedule, prompt);
        selectNo = prompt.inputInt(" 1) 스케줄 참가자 추가 \n 2) 스케줄 참가자 삭제 \n 0) 나가기 \n > ");

        if (selectNo == 1) {
          try {
            int result = scheduleDao.scheduleAddParticipant(schedule.getNo(),
                prompt.inputInt("추가할 참가자 번호?"));

            if (result == -1) {
              prompt.println("이미 참가중인 멤버입니다.");
            } else if (result == -2) {
              prompt.println("존재하지 않는 멤버입니다.");
            } else {
              sqlSessionFactory.openSession(false).commit();
              prompt.println("추가했습니다.");
            }
            prompt.end();

          } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
          }
        } else if (selectNo == 2) {
          try {
            int result = scheduleDao.scheduleDeleteParticipant(schedule.getNo(),
                prompt.inputInt("삭제할 참가자 번호?"));

            if (result == -1) {
              prompt.println("참여하지 않는 멤버입니다.");
            } else if (result == -2) {
              prompt.println("존재하지 않는 멤버입니다.");
            } else {
              sqlSessionFactory.openSession(false).commit();
              prompt.println("삭제했습니다.");
            }
            prompt.end();

          } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
          }
        }
      } while (selectNo != 0);

    } else {
      printParticipants(schedule, prompt);
    }
  }

  private void printParticipants(Schedule schedule, BreadcrumbPrompt prompt) throws IOException {

    List<Member> participantList = scheduleDao.findAllParticipatedMember(schedule.getNo());
    prompt.printf("스케줄 참가자 명단\n");
    prompt.printf(" 번호 | 이름\n");
    for (Member m : participantList.toArray(new Member[0])) {
      prompt.printf(" %-4d | %s\n", m.getNo(), m.getName());
    }
    prompt.println("");
    prompt.writeBuf();
  }
}


