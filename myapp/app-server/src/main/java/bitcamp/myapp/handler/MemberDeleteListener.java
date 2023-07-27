package bitcamp.myapp.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public class MemberDeleteListener implements ActionListener {
  MemberDao memberDao;
  SqlSessionFactory sqlSessionFactory;

  public MemberDeleteListener(MemberDao memberDao, SqlSessionFactory sqlSessionFactory) {

    this.memberDao = memberDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    int no = prompt.inputInt("번호? ");
    if (no != ((Member) prompt.getAttribute("loginUser")).getNo()) {
      prompt.println("본인 계정만 삭제 가능합니다.");
      return;
    }
    try {
      memberDao.delete(no);
      sqlSessionFactory.openSession(false).commit();
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }

}
