package com.bitcamp.myapp.listener;

import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.dao.MySQLBoardDao;
import com.bitcamp.myapp.dao.MySQLMemberDao;
import com.bitcamp.myapp.dao.MySQLScheduleDao;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.config.NcpConfig;
import com.bitcamp.myapp.service.NcpObjectStorageService;
import com.bitcamp.util.SqlSessionFactoryProxy;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

// 웹애플리케이션에 실행에 필요한 설정이나 객체를 준비한다.
// 언제? 웹애플리케이션 시작될 때!
//
@WebListener
public class ContextLoaderListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {

    // 준비한 객체를 담을 수 있도록 보관소를 꺼낸다.
    ServletContext ctx = sce.getServletContext();

    // 서블릿들이 공통으로 사용할 객체를 준비한다.
    try {
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryProxy(
          new SqlSessionFactoryBuilder().build(
              Resources.getResourceAsStream(ctx.getInitParameter("mybatis-config"))));
      BoardDao boardDao = new MySQLBoardDao(sqlSessionFactory);
      MemberDao memberDao = new MySQLMemberDao(sqlSessionFactory);
      ScheduleDao scheduleDao = new MySQLScheduleDao(sqlSessionFactory);
      NcpObjectStorageService ncpObjectStorageService = new NcpObjectStorageService(
          new NcpConfig());

      // 준비한 객체를 꺼내 쓸 수 있도록 보관소에 저장한다.
      ctx.setAttribute("sqlSessionFactory", sqlSessionFactory);
      ctx.setAttribute("boardDao", boardDao);
      ctx.setAttribute("memberDao", memberDao);
      ctx.setAttribute("scheduleDao", scheduleDao);
      ctx.setAttribute("ncpObjectStorageService", ncpObjectStorageService);

      System.out.println("ContextLoaderListener.contextInitialized() - 공통 객체 준비 완료!");

    } catch (Exception e) {
      System.out.println("ContextLoaderListener.contextInitialized() 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
