package com.bitcamp.myapp.config;

import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.service.BoardService;
import com.bitcamp.myapp.service.DefaultBoardService;
import com.bitcamp.myapp.service.DefaultMemberService;
import com.bitcamp.myapp.service.DefaultScheduleService;
import com.bitcamp.myapp.service.MemberService;
import com.bitcamp.myapp.service.ScheduleService;
import com.bitcamp.util.TransactionProxyBuilder;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

// Application을 실행하는데 필요한 객체를 설정하는 일을 한다.
//
@ComponentScan(basePackages = {
    "com.bitcamp.myapp.dao",
    "com.bitcamp.myapp.controller",
    "com.bitcamp.myapp.service"})
@PropertySource({"classpath:com/bitcamp/myapp/config/ncloud/jdbc.properties"})
@MapperScan("com.bitcamp.myapp.dao") // Mybatis가 자동으로 생성할 DAO 객체의 인터페이스 패키지 지정
public class AppConfig {

  public AppConfig() {
    System.out.println("AppConfig() 호출됨!");

    // Mybatis에서 Log4j 2.x 버전을 사용하도록 활성화시킨다.
    // 활성화시키지 않으면 Mybatis에서 로그를 출력하지 않는다.
    org.apache.ibatis.logging.LogFactory.useLog4J2Logging();
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext appCtx)
      throws Exception {
    System.out.println("AppConfig.sqlSessionFactory() 호출됨!");

    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(dataSource);
    factoryBean.setTypeAliasesPackage("com.bitcamp.myapp.vo");
    factoryBean.setMapperLocations(
        appCtx.getResources("classpath:com/bitcamp/myapp/dao/mysql/*Dao.xml"));

    return factoryBean.getObject();
  }

  @Bean
  public DataSource dataSource(
      @Value("${jdbc.driver}") String driver,
      @Value("${jdbc.url}") String url,
      @Value("${jdbc.username}") String username,
      @Value("${jdbc.password}") String password) {
    System.out.println("AppConfig.dataSource() 호출됨!");

    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName(driver);
    ds.setUrl(url);
    ds.setUsername(username);
    ds.setPassword(password);

    return ds;
  }

  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    System.out.println("AppConfig.transactionManager() 호출됨!");

    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  public TransactionProxyBuilder txProxyBuilder(PlatformTransactionManager txManager) {
    // 주어진 객체에 트랜잭션 다루는 기능을 덧붙여서 새로운 객체를 만드는 일을 한다.
    return new TransactionProxyBuilder(txManager);
  }

  @Bean
  public BoardService boardService(TransactionProxyBuilder txProxyBuilder, BoardDao boardDao) {
    // 서비스 객체 + 트랜잭션 다루는 기능  => 리턴
    return (BoardService) txProxyBuilder.build(new DefaultBoardService(boardDao));
  }

  @Bean
  public MemberService memberService(TransactionProxyBuilder txProxyBuilder, MemberDao memberDao) {
    // 서비스 객체 + 트랜잭션 다루는 기능  => 리턴
    return (MemberService) txProxyBuilder.build(new DefaultMemberService(memberDao));
  }

  @Bean
  public ScheduleService scheduleService(
      TransactionProxyBuilder txProxyBuilder,
      ScheduleDao scheduleDao) {
    // 서비스 객체 + 트랜잭션 다루는 기능  => 리턴
    return (ScheduleService) txProxyBuilder.build(new DefaultScheduleService(scheduleDao));
  }

}
