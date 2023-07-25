package com.bitcamp.myapp.handler;

import java.io.IOException;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;

public class MemberDeleteListener implements ActionListener {
  MemberDao memberDao;

  public MemberDeleteListener(MemberDao memberDao) {

    this.memberDao = memberDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    int no = prompt.inputInt("번호? ");
    if (no != ((Member) prompt.getAttribute("loginUser")).getNo()) {
      prompt.println("본인 계정만 삭제 가능합니다.");
      return;
    }
    memberDao.remove(no);
  }

}
