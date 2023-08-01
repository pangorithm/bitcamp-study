package com.bitcamp.myapp.handler;

import java.io.IOException;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.util.BreadcrumbPrompt;
import com.bitcamp.util.Component;

@Component("/auth/login")
public class LoginListener implements MemberActionListener {

  MemberDao memberDao;

  public LoginListener(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    while (true) {
      Member member = new Member();
      member.setEmail(prompt.inputString("이메일? "));
      member.setPassword(prompt.inputString("암호? "));

      Member loginUser = memberDao.findByEmailAndPassword(member);
      if (loginUser == null) {
        prompt.println("회원 정보가 일치하지 않습니다.");

      } else {
        prompt.setAttribute("loginUser", loginUser);
        break;
      }
      prompt.end();
    }
  }


}
