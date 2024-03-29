package com.bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.dao.MySQLBoardDao;
import com.bitcamp.myapp.dao.MySQLMemberDao;
import com.bitcamp.myapp.dao.MySQLScheduleDao;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.util.SqlSessionFactoryProxy;

@WebServlet(value = "/init", loadOnStartup = 1)
public class InitServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public static SqlSessionFactory sqlSessionFactory;
  public static BoardDao boardDao;
  public static MemberDao memberDao;
  public static ScheduleDao scheduleDao;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    System.out.println("InitServlet.init() 호출됨!");

    try {
      sqlSessionFactory =
          new SqlSessionFactoryProxy(
              new SqlSessionFactoryBuilder()
                  .build(
                      Resources
                          .getResourceAsStream("com/bitcamp/myapp/config/mybatis-config.xml")));

      boardDao = new MySQLBoardDao(sqlSessionFactory);
      memberDao = new MySQLMemberDao(sqlSessionFactory);
      scheduleDao = new MySQLScheduleDao(sqlSessionFactory);


    } catch (IOException e) {
      System.out.println("InitServlet.init() 실행 중 오류 발생!");
      e.printStackTrace();
    }

  }

  @Override
  public void service(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>시작</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>애플리케이션 준비!</h1>");

    out.println("<p>애플맄케이션을 실행할 준비를 완료했습니다!</p>");

    out.println("</body>");
    out.println("</html>");
  }


}
