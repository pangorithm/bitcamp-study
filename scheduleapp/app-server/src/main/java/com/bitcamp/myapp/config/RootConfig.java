package com.bitcamp.myapp.config;

import org.springframework.context.annotation.ComponentScan;

// Application을 실행하는데 필요한 객체를 설정하는 일을 한다.
//
@ComponentScan(
    basePackages = {
        "com.bitcamp.myapp.config",
        "com.bitcamp.myapp.service"}
)
public class RootConfig {

  {
    System.out.println("RootConfig() 호출됨!");
  }

}
