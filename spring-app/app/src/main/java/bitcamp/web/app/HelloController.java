package bitcamp.web.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

  {
    System.out.println("HelloController 생성됨");
  }

  @RequestMapping("/hello")
  @ResponseBody
  public String hello() throws Exception {
    return "<html><body><h1>Hello!</h1></body></html>";
  }
}
