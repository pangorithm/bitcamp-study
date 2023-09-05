package bitcamp.myapp.servlet;

import bitcamp.myapp.config.AppConfig;
import bitcamp.myapp.config.NcpConfig;
import bitcamp.myapp.controller.RequestMapping;
import bitcamp.myapp.controller.RequestParam;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet(value = "/app/*", loadOnStartup = 1)
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class DispatcherServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  AnnotationConfigApplicationContext iocContainer;

  Map<String, RequestHandlerMapping> handlerMap = new HashMap<>();

  @Override
  public void init() throws ServletException {
    System.out.println("DispatcherServlet() 호출됨!");
    iocContainer = new AnnotationConfigApplicationContext(AppConfig.class, NcpConfig.class);

    String[] names = iocContainer.getBeanDefinitionNames();
    for (String name : names) {
      Object bean = iocContainer.getBean(name);
      resisterRequestHandler(bean);
    }
  }

  private void resisterRequestHandler(Object bean) {
    System.out.printf("=> %s\n", bean.getClass().getName());

    Method[] methods = bean.getClass().getDeclaredMethods();
    for (Method m : methods) {
      RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);
      if (requestMapping == null) {
        continue;
      }

      // request handler 메서드를 맵에 등록한다.
      handlerMap.put(requestMapping.value(), new RequestHandlerMapping(bean, m));
      System.out.printf("      %s - %s\n", requestMapping.value(), m.getName());
    }
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String pageControllerPath = request.getPathInfo();

    response.setContentType("text/html;charset=UTF-8");

    // 클라이언트가 요청한 URL의 요청 핸들러 정보를 찾는다.
    RequestHandlerMapping requestHandlerMapping = handlerMap.get(pageControllerPath);
    if (requestHandlerMapping == null) {
      throw new ServletException("요청을 처리할 핸들러가 없습니다.");
    }

    // request handler 호출하기
    try {
      Object[] arguments = prepareArguments(requestHandlerMapping.handler, request, response);
      String viewUrl = (String) requestHandlerMapping.handler.invoke(
          requestHandlerMapping.controller, arguments);
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

  private Object[] prepareArguments(
      Method handler,
      HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Parameter[] params = handler.getParameters();
    ArrayList<Object> argments = new ArrayList<>();

    System.out.printf("%s(): ", handler.getName());
    for (Parameter p : params) {
      System.out.printf("%s(%s), ", p.getType().getName(), p.getName());
      if (p.getType() == HttpServletRequest.class || p.getType() == ServletRequest.class) {
        argments.add(request);
      } else if (p.getType() == HttpServletResponse.class || p.getType() == ServletResponse.class) {
        argments.add(response);
      } else if (p.getType() == HttpSession.class) {
        argments.add(request.getSession());
      } else if (p.getType() == String.class) {
        argments.add(request.getParameter(p.getAnnotation(RequestParam.class).value()));
      } else if (p.getType() == int.class) {
        argments.add(
            Integer.parseInt(request.getParameter(p.getAnnotation(RequestParam.class).value())));
      } else if (p.getType() == char.class) {
        argments.add(request.getParameter(p.getAnnotation(RequestParam.class).value()).charAt(0));
      } else if (p.getType() == Part.class) {
        argments.add(request.getPart(p.getAnnotation(RequestParam.class).value()));
      } else {
        argments.add(null);
      }

    }
    System.out.println();

    return argments.toArray();
  }

  static class RequestHandlerMapping {

    Object controller;
    Method handler;

    public RequestHandlerMapping() {
    }

    public RequestHandlerMapping(Object controller, Method handler) {
      this.controller = controller;
      this.handler = handler;
    }
  }
}
