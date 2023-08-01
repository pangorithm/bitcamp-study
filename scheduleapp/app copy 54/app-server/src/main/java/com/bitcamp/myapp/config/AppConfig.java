package com.bitcamp.myapp.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.bitcamp.util.Bean;
import com.bitcamp.util.ComponentScan;
import com.bitcamp.util.SqlSessionFactoryProxy;

// Application을 실행하는데 필요한 객체를 설정하는 일을 한다.

@ComponentScan(basePackages = {"com.bitcamp.myapp.dao", "com.bitcamp.myapp.handler"})
public class AppConfig {

  // Mybatis 준비
  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    return new SqlSessionFactoryProxy(new SqlSessionFactoryBuilder()
        .build(Resources.getResourceAsStream("com/bitcamp/myapp/config/mybatis-config.xml")));
  }

}
