package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.ClientApp;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.util.BreadcrumbPrompt;

public class LoginListener implements MemberActionListener {

  MemberDao memberDao;

  public LoginListener(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    while (true) {
      Member member = new Member();
      member.setEmail(prompt.inputString("이메일? "));
      member.setPassword(prompt.inputString("암호? "));

      Member loginUser = memberDao.findByEmailAndPassword(member);
      if (loginUser == null) {
        System.out.println("회원 정보가 일치하지 않습니다.");

      } else {
        ClientApp.loginUser = loginUser;
        break;
      }
    }
  }


}
