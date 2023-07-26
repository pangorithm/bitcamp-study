package com.bitcamp.myapp.handler;

import java.io.IOException;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.DataSource;

public class MemberAddListener implements MemberActionListener {

  MemberDao memberDao;
  DataSource ds;

  public MemberAddListener(MemberDao memberDao, DataSource ds) {
    this.memberDao = memberDao;
    this.ds = ds;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    Member member = new Member();
    member.setName(prompt.inputString("이름? "));
    member.setEmail(prompt.inputString("이메일? "));
    member.setPassword(prompt.inputString("암호? "));
    member.setGender(MemberActionListener.inputGender((char) 0, prompt));

    try {
      memberDao.insert(member);
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
