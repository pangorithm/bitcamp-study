package bitcamp.myapp.controller;

import bitcamp.myapp.dao.MemberDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component("/member/detail")
public class MemberDetailController implements PageController {

  private static final long serialVersionUID = 1L;
  MemberDao memberDao;

  public MemberDetailController(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setAttribute("member", memberDao.findBy(Integer.parseInt(request.getParameter("no"))));
    response.setContentType("text/html;charset=UTF-8");
    return "/WEB-INF/jsp/member/detail.jsp";
  }
}
