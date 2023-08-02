/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.bitcamp.myapp;

import org.apache.ibatis.session.SqlSessionFactory;
import com.bitcamp.myapp.config.AppConfig;
import com.bitcamp.util.ApplicationContext;
import com.bitcamp.util.DispatcherServlet;
import com.bitcamp.util.HttpServletRequest;
import com.bitcamp.util.HttpServletResponse;
import com.bitcamp.util.SqlSessionFactoryProxy;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.NettyOutbound;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

public class ServerApp {

  ApplicationContext iocContainer;
  DispatcherServlet dispatcherServlet;

  int port;

  public ServerApp(int port) throws Exception {

    this.port = port;
    iocContainer = new ApplicationContext(AppConfig.class);
    dispatcherServlet = new DispatcherServlet(iocContainer);
  }

  public void close() throws Exception {

  }

  public static void main(String[] args) throws Exception {
    ServerApp app = new ServerApp(8888);
    app.execute();
    app.close();
  }

  public void execute() {
    DisposableServer server =
        HttpServer.create()
            .port(8888)
            .handle((request, response) -> processRequest(request, response))
            .bindNow();
    System.out.println("서버 실행 됨!");

    server.onDispose().block();
    System.out.println("서버 종료됨!");
  }

  private NettyOutbound processRequest(HttpServerRequest request, HttpServerResponse response) {
    HttpServletRequest request2 = new HttpServletRequest(request);
    HttpServletResponse response2 = new HttpServletResponse(response);

    try {
      dispatcherServlet.service(request2, response2);

      // HTTP 응답 프로토콜의 헤더 설정
      response.addHeader("Content-Type", response2.getContentType());

      // 서블릿이 출력한 문자열을 꺼내 HTTP 프로토콜에 맞춰 응답한다.
      return response.sendString(Mono.just(response2.getContent()));

    } catch (Exception e) {
      e.printStackTrace();
      return response.sendString(Mono.just(response2.getContent()));

    } finally {
      SqlSessionFactoryProxy sqlSessionFactoryProxy =
          (SqlSessionFactoryProxy) iocContainer.getBean(SqlSessionFactory.class);
      sqlSessionFactoryProxy.clean();
    }
  }
}


