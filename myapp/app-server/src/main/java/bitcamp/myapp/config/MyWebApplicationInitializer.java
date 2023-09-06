package bitcamp.myapp.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MyWebApplicationInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext sc) throws ServletException {
    System.out.println("MyWebApplicationInitializer.onStartup() 호출됨!");

    // DispatcherServlet이 사용할 IoC 컨테이너를 준비한다.
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.register(AppConfig.class);

    // DispatcherServlet을 생성하여 서블릿 컨테이너에 등록한다.
    DispatcherServlet servlet = new DispatcherServlet(context);
    ServletRegistration.Dynamic registration = sc.addServlet("app", servlet);
    registration.setLoadOnStartup(1);
    registration.addMapping("/app/*");
    registration.setMultipartConfig(
        new MultipartConfigElement("temp", 10000000, 15000000, 1000000));
  }
}