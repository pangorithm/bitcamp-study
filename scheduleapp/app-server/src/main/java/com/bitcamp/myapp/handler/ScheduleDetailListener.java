package com.bitcamp.myapp.handler;

import java.sql.Timestamp;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;

public class ScheduleDetailListener implements ActionListener {

  ScheduleDao scheduleDao;

  public ScheduleDetailListener(ScheduleDao scheduleDao) {
    this.scheduleDao = scheduleDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    int scheduleNo = prompt.inputInt("번호? ");

    Schedule schedule = scheduleDao.findBy(scheduleNo);
    if (schedule == null) {
      System.out.println("해당 번호의 스케줄이 없습니다!");
      return;
    }

    System.out.printf("번호: %d\n", schedule.getNo());
    System.out.printf("제목: %s\n", schedule.getScheduleTitle());
    System.out.printf("시작 시간: %s\n", new Timestamp(schedule.getStartTime()).toString());
    System.out.printf("종료 시간: %s\n", new Timestamp(schedule.getEndTime()).toString());
    System.out.printf("소유자: %s\n", schedule.getOwner().getName());
    // 소유자가 무조건 로그인 한 사람으로 나오는 오류

    // 참가중인 멤버 목록 출력 필요

    while (prompt.promptContinue(prompt.inputString("스케줄 참가자를 추가하시겠습니까?(y/N)"))) {
      if (scheduleDao.scheduleAddParticipant(schedule.getNo(),
          prompt.inputInt("추가할 참가자 번호?")) == -1) {
        System.out.println("이미 참가중인 멤버입니다.");
      }
    }

  }
}


