package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.ServerApp;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;

public class MemberDeleteListener implements ActionListener {
  MemberDao memberDao;

  public MemberDeleteListener(MemberDao memberDao) {

    this.memberDao = memberDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    int no = prompt.inputInt("번호? ");
    if (no != ServerApp.loginUser.getNo()) {
      System.out.println("본인 계정만 삭제 가능합니다.");
      return;
    }
    memberDao.remove(no);
  }

}
