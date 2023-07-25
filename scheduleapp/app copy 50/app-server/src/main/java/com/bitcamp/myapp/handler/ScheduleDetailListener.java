package com.bitcamp.myapp.handler;

import java.io.IOException;
import java.sql.Timestamp;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;

public class ScheduleDetailListener implements ActionListener {

  ScheduleDao scheduleDao;

  public ScheduleDetailListener(ScheduleDao scheduleDao) {
    this.scheduleDao = scheduleDao;
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
    prompt.printf("시작 시간: %s\n", new Timestamp(schedule.getStartTime()).toString());
    prompt.printf("종료 시간: %s\n", new Timestamp(schedule.getEndTime()).toString());
    prompt.printf("소유자: %s\n", schedule.getOwner().getName());
    // 소유자가 무조건 로그인 한 사람으로 나오는 오류

    // 참가중인 멤버 목록 출력 필요

    prompt.end();
    while (prompt.promptContinue(prompt.inputString("스케줄 참가자를 추가하시겠습니까?(y/N)"))) {
      if (scheduleDao.scheduleAddParticipant(schedule.getNo(),
          prompt.inputInt("추가할 참가자 번호?")) == -1) {
        prompt.println("이미 참가중인 멤버입니다.");
        prompt.end();
      }
    }

  }
}


