package com.bitcamp.myapp.servlet;

import com.bitcamp.myapp.controller.BoardAddController;
import com.bitcamp.myapp.controller.BoardDeleteController;
import com.bitcamp.myapp.controller.BoardDetailController;
import com.bitcamp.myapp.controller.BoardFileDeleteController;
import com.bitcamp.myapp.controller.BoardListController;
import com.bitcamp.myapp.controller.BoardUpdateController;
import com.bitcamp.myapp.controller.HomeController;
import com.bitcamp.myapp.controller.LoginController;
import com.bitcamp.myapp.controller.LogoutController;
import com.bitcamp.myapp.controller.MemberAddController;
import com.bitcamp.myapp.controller.MemberAddressAddController;
import com.bitcamp.myapp.controller.MemberAddressDeleteController;
import com.bitcamp.myapp.controller.MemberDeleteController;
import com.bitcamp.myapp.controller.MemberDetailController;
import com.bitcamp.myapp.controller.MemberListController;
import com.bitcamp.myapp.controller.MemberUpdateController;
import com.bitcamp.myapp.controller.PageController;
import com.bitcamp.myapp.controller.ScheduleAddController;
import com.bitcamp.myapp.controller.ScheduleDeleteController;
import com.bitcamp.myapp.controller.ScheduleDetailController;
import com.bitcamp.myapp.controller.ScheduleListController;
import com.bitcamp.myapp.controller.ScheduleParticipantAdd;
import com.bitcamp.myapp.controller.ScheduleParticipantDelete;
import com.bitcamp.myapp.controller.ScheduleSearchController;
import com.bitcamp.myapp.controller.ScheduleUpdateController;
import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.util.NcpObjectStorageService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;

@WebServlet("/app/*")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class DispatcherServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  Map<String, PageController> controllerMap = new HashMap<>();

  @Override
  public void init() throws ServletException {
    ScheduleDao scheduleDao = (ScheduleDao) this.getServletContext().getAttribute("scheduleDao");
    MemberDao memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");
    BoardDao boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");
    NcpObjectStorageService ncpObjectStorageService = (NcpObjectStorageService) this.getServletContext()
        .getAttribute("ncpObjectStorageService");

    controllerMap.put("/", new HomeController());
    controllerMap.put("/auth/login", new LoginController(memberDao));
    controllerMap.put("/auth/logout", new LogoutController());
    controllerMap.put("/member/list", new MemberListController(memberDao));
    controllerMap.put("/member/add",
        new MemberAddController(memberDao, sqlSessionFactory, ncpObjectStorageService));
    controllerMap.put("/member/detail", new MemberDetailController(memberDao));
    controllerMap.put("/member/update",
        new MemberUpdateController(memberDao, sqlSessionFactory, ncpObjectStorageService));
    controllerMap.put("/member/delete", new MemberDeleteController(memberDao, sqlSessionFactory));
    controllerMap.put("/member/addressAdd",
        new MemberAddressAddController(memberDao, sqlSessionFactory));
    controllerMap.put("/member/addressDelete",
        new MemberAddressDeleteController(memberDao, sqlSessionFactory));

    controllerMap.put("/board/list", new BoardListController(boardDao));
    controllerMap.put("/board/add",
        new BoardAddController(boardDao, sqlSessionFactory, ncpObjectStorageService));
    controllerMap.put("/board/detail", new BoardDetailController(boardDao, sqlSessionFactory));
    controllerMap.put("/board/update",
        new BoardUpdateController(boardDao, sqlSessionFactory, ncpObjectStorageService));
    controllerMap.put("/board/delete", new BoardDeleteController(boardDao, sqlSessionFactory));
    controllerMap.put("/board/fileDelete",
        new BoardFileDeleteController(boardDao, sqlSessionFactory));

    controllerMap.put("/schedule/add", new ScheduleAddController(scheduleDao, sqlSessionFactory));
    controllerMap.put("/schedule/delete",
        new ScheduleDeleteController(scheduleDao, sqlSessionFactory));
    controllerMap.put("/schedule/detail",
        new ScheduleDetailController(scheduleDao));
    controllerMap.put("/schedule/list", new ScheduleListController(scheduleDao));
    controllerMap.put("/schedule/participantAdd",
        new ScheduleParticipantAdd(scheduleDao, sqlSessionFactory));
    controllerMap.put("/schedule/participantDelete",
        new ScheduleParticipantDelete(scheduleDao, sqlSessionFactory));
    controllerMap.put("/schedule/search",
        new ScheduleSearchController(scheduleDao));
    controllerMap.put("/schedule/update",
        new ScheduleUpdateController(scheduleDao, sqlSessionFactory));
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String pageControllerPath = request.getPathInfo();
    System.out.println(pageControllerPath);
    response.setContentType("text/html;charset=UTF-8");

    // 클라이언트가 요청한 페이지 컨트롤러를 찾는다.
    PageController pageController = controllerMap.get(pageControllerPath);
    if (pageController == null) {
      throw new ServletException("해당 요청을 처리할 수 없습니다!");
    }

    // 페이지 컨트롤러를 실행한다.
    try {
      String viewUrl = pageController.execute(request, response);
      System.out.println(viewUrl);
      if (viewUrl.startsWith("redirect:")) {
        response.sendRedirect(viewUrl.substring(9)); // 예) redirect:/app/board/list
      } else {
        request.getRequestDispatcher(viewUrl).include(request, response);
      }

    } catch (Exception e) {
      // 페이지 컨트롤러 실행 중 오류가 발생했다면, 예외를 던진다.
      throw new ServletException("요청 처리 중 오류 발생!", e);
    }

  }
}
