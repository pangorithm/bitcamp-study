package bitcamp.myapp.handler;

import bitcamp.myapp.dao.MemberDao;
import bitcamp.util.Component;
import bitcamp.util.HttpServletRequest;
import bitcamp.util.HttpServletResponse;
import bitcamp.util.Servlet;

@Component("/auth/login")
public class LoginServlet implements Servlet {

  MemberDao memberDao;

  public LoginServlet(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
    // while (true) {
    // Member member = new Member();
    // member.setEmail(prompt.inputString("이메일? "));
    // member.setPassword(prompt.inputString("암호? "));
    //
    // Member loginUser = memberDao.findByEmailAndPassword(member);
    // if (loginUser == null) {
    // prompt.println("회원 정보가 일치하지 않습니다.");
    //
    // } else {
    // prompt.setAttribute("loginUser", loginUser);
    // break;
    // }
    // prompt.end();
    // }
  }


}
