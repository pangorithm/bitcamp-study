package bitcamp.myapp.servlet;

import bitcamp.myapp.config.AppConfig;
import bitcamp.myapp.config.NcpConfig;
import bitcamp.myapp.controller.RequestMapping;
import bitcamp.myapp.controller.RequestParam;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
    if (request.getContentType() != null
        && request.getContentType().toLowerCase().equals("multipart/form-data")) {
      request.getParts();
    }

    response.setContentType("text/html;charset=UTF-8");

    // 클라이언트가 요청한 URL의 요청 핸들러 정보를 찾는다.
    RequestHandlerMapping requestHandlerMapping = handlerMap.get(pageControllerPath);
    if (requestHandlerMapping == null) {
      throw new ServletException("요청을 처리할 핸들러가 없습니다.");
    }

    // request handler 호출하기
    try {
      Map<String, Object> model = new HashMap<>();
      Object[] arguments = prepareArguments(
          requestHandlerMapping.handler, request, response, model);

      String viewUrl = (String) requestHandlerMapping.handler
          .invoke(requestHandlerMapping.controller, arguments);

      // model 객체에 저장된 값을 ServletRequest 보관소로 옮긴다.
      Set<Entry<String, Object>> entrySet = model.entrySet();
      for (Entry<String, Object> entry : entrySet) {
        request.setAttribute(entry.getKey(), entry.getValue());
      }

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
      HttpServletResponse response,
      Map<String, Object> model) throws Exception {
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
      } else if (p.getType() == Map.class) {
        argments.add(model);
      } else if (p.getType() == Part.class) {
        argments.add(request.getPart(p.getAnnotation(RequestParam.class).value()));
      } else if (p.getType() == Part[].class) {
        String paramName = p.getAnnotation(RequestParam.class).value();
        ArrayList<Part> parts = new ArrayList<>();
        for (Part part : request.getParts()) {
          if (part.getName().equals(paramName)) {
            parts.add(part);
          }
        }
        argments.add(parts.toArray(new Part[]{}));
      } else {
        argments.add(getValueObject(p.getType(), request));
      }

    }
    System.out.println();

    return argments.toArray();
  }

  private Object getValueObject(Class<?> clazz, HttpServletRequest request) throws Exception {
    // 클래스의 생성자를 알아낸다
    Constructor<?> constructor = clazz.getConstructor();

    // 생성자를 통해 인스턴스를 생성한다
    Object obj = constructor.newInstance();

    // 클래스의 메서드 목록을 알아낸다
    Method[] methods = clazz.getMethods();

    // 세터 메서드를 찾아 호출한다
    for (Method m : methods) {
      if (!m.getName().startsWith("set")) {
        continue;
      }

      // 세터 메서드의 이름을 이용하여 프로퍼티 이름을 알아낸다.
      String propName = new StringBuilder()
          .append(m.getName().substring(3, 4).toLowerCase())
          .append(m.getName().substring(4))
          .toString();

      // 프로퍼티 이름과 똑같은 이름으로 넘어온 요청 파라미터 값을 꺼낸다.
      String paramValue = request.getParameter(propName);
      if (paramValue == null) {
        continue;
      }

      // 세터 메서드를 호출하여 파라미터 값을 저장한다.
      m.invoke(obj, strToPrimitiveType(paramValue, m.getParameters()[0].getType()));

    }

    return obj;
  }

  private Object strToPrimitiveType(String value, Class<?> type) {
    if (type == String.class) {
      return value;
    } else if (type == int.class) {
      return Integer.parseInt(value);
    } else if (type == char.class) {
      return value.charAt(0);
    } else if (type == boolean.class) {
      return Boolean.valueOf(value);
    } else {
      return null;
    }
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
