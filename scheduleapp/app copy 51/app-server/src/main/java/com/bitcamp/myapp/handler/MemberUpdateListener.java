package com.bitcamp.myapp.handler;

import java.io.IOException;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.DataSource;

public class MemberUpdateListener implements MemberActionListener {
  MemberDao memberDao;
  DataSource ds;

  public MemberUpdateListener(MemberDao memberDao, DataSource ds) {
    this.memberDao = memberDao;
    this.ds = ds;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    int memberNo = prompt.inputInt("번호? ");

    Member m = memberDao.findBy(memberNo);
    if (m == null) {
      prompt.println("해당 번호의 회원이 없습니다!");
      return;
    }
    if (!m.equals(prompt.getAttribute("loginUser"))) {
      prompt.println("본인의 계정만 수정 가능합니다.");
      return;
    }

    m.setName(prompt.inputString("이름(%s)? ", m.getName()));
    m.setEmail(prompt.inputString("이메일(%s)? ", m.getEmail()));
    m.setPassword(prompt.inputString("새암호? "));
    m.setGender(MemberActionListener.inputGender(m.getGender(), prompt));

    memberDao.update(m);
    try {
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
