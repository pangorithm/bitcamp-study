package bitcamp.myapp.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import bitcamp.util.SqlSessionFactoryProxy;

// Application을 실행하는데 필요한 객체를 설정하는 일을 한다.
public class AppConfig {
  public SqlSessionFactory createSqlSessionFactory() throws Exception {

    // Mybatis 준비
    return new SqlSessionFactoryProxy(new SqlSessionFactoryBuilder()
        .build(Resources.getResourceAsStream("bitcamp/myapp/config/mybatis-config.xml")));
  }
}
