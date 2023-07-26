package com.bitcamp.myapp.handler;

import java.io.IOException;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.DataSource;

public class MemberDeleteListener implements ActionListener {
  MemberDao memberDao;
  DataSource ds;

  public MemberDeleteListener(MemberDao memberDao, DataSource ds) {

    this.memberDao = memberDao;
    this.ds = ds;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    int no = prompt.inputInt("번호? ");
    if (no != ((Member) prompt.getAttribute("loginUser")).getNo()) {
      prompt.println("본인 계정만 삭제 가능합니다.");
      return;
    }
    try {
      memberDao.remove(no);
      ds.getConnection().commit();
    } catch (Exception e) {
      try {
        ds.getConnection().rollback();
      } catch (Exception e2) {
        // TODO: handle exception
      }
    }
  }

}
