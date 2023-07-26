package com.bitcamp.myapp.handler;

import java.util.List;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;

public class MemberListListener implements ActionListener {

  MemberDao memberDao;

  public MemberListListener(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    prompt.println("---------------------------------------");
    prompt.println("번호, 이름, 이메일, 성별");
    prompt.println("---------------------------------------");

    // 목록에서 데이터를 대신 꺼내주는 객체를 얻는다.
    List<Member> list = memberDao.list();

    for (Member m : list) {
      prompt.printf("%d, %s, %s, %s\n", m.getNo(), m.getName(), m.getEmail(),
          m.getGender() == 'M' ? "남성" : "여성");
    }
  }
}
