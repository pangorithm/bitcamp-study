package com.bitcamp.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

// Application을 실행하는데 필요한 객체를 설정하는 일을 한다.
//
@ComponentScan(
    basePackages = "com.bitcamp.myapp.controller",
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*ScheduleController"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*BoardController"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*HomeController"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*AuthController")
    }
//    , includeFilters = @Filter(
//    type = FilterType.ASPECTJ,
//    pattern = "bitcamp.myapp.controller.MemberController"
//)
)
public class AdminConfig {

  public AdminConfig() {
    System.out.println("AdminConfig() 호출됨!");
  }

  @Bean
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
  }
}
