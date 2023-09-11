// 세션 다루기 - @SessionAttributes를 사용하는 예
package bitcamp.app2;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/c03_4")

// 세션에 보관된 값 중에서 현재 페이지 컨트롤러에서 사용할 값을 지정한다.
// 또한 세션에 보관할 값이기도 하다.
@SessionAttributes({"name", "age", "tel"})
public class Controller03_4 {

  // 테스트:
  // http://.../app2/c03_4/step0
  @GetMapping(value = "step0", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String handler(HttpSession session) {
    // 프론트 컨트롤러에게 HttpSession을 요구하면
    // 기존에 만든게 있다면 그 객체를 넘겨 줄 것이고,
    // 없다면 새로 만들어 넘겨 줄 것이다.
    // 어찌 되었든 이 요청 핸들러를 실행하는 순간 HttpSession 객체를 존재하게 된다.
    // 보통 로그인 과정에서 HttpSession 객체가 준비될 것이고,
    // 그 전에 JSP를 실행하는 과정에서 HttpSession 객체가 생성될 것이다.
    // 따라서 이 메서드처럼 일부러 HttpSession 객체를 만들가 할 필요는 없다.
    // 다만 @SessionAttribute와 @ModelAttribute를 테스트하기 위해
    // 예제를 실행하는 과정에서 HttpSession 객체를 미리 준비할 필요가 있기 때문에
    // 어거지로 이 메서드를 만든 것이다.
    return "세션 준비했음!";
  }

  // 테스트:
  // http://.../app2/c03_4/step1?name=hong
  @GetMapping(value = "step1", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String handler1(String name, Model model) {
    model.addAttribute("name", name);
    return "이름 저장했음";
  }

  // 테스트:
  // http://.../app2/c03_4/step2?age=20
  @GetMapping(value = "step2", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String handler2(int age, Model model) {
    model.addAttribute("age", age);
    return "나이 저장했음";
  }

  // 테스트:
  // http://.../app2/c03_4/step3?tel=1111
  @GetMapping(value = "step3", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String handler3(String tel, Model model) {
    model.addAttribute("tel", tel);
    return "전화번호 저장했음";
  }

  // 테스트:
  // http://.../app2/c03_4/stepx
  @GetMapping(value = "stepx", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String handler3(Model model) {
    model.addAttribute("name", "홍길동");
    model.addAttribute("age", "20");
    model.addAttribute("tel", "01099998888");
    return "전화번호 저장했음";
  }

  // 테스트:
  // http://.../app2/c03_4/step4
  @GetMapping(value = "step4", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String handler4(
      @ModelAttribute("name") String name,
      @ModelAttribute("age") int age,
      @ModelAttribute("tel") String tel
  ) {

    // 이 페이지 컨트롤러가 작업을 하는 동안 세션에 임시 보관했던 값들은
    // 예를 들어, "DB에 저장한 후" 세션에서 제거한다.

    return String.format("이름=%s, 나이=%s, 전화=%s 를 DB에 저장했음!\n",
        name,
        age,
        tel);
  }

  // 테스트:
  // http://.../app2/c03_4/test1
  @GetMapping(value = "test1", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String test1(HttpSession session) {

    // 이 페이지 컨트롤러가 작업을 하는 동안 세션에 임시 보관했던 값들은
    // 예를 들어, "DB에 저장한 후" 세션에서 제거한다.

    return String.format("이름=%s, 나이=%s, 전화=%s 를 DB에 저장했음!\n",
        session.getAttribute("name"),
        session.getAttribute("age"),
        session.getAttribute("tel"));
  }

  // 테스트:
  // http://.../app2/c03_4/test2
  @GetMapping(value = "test2", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String test2(HttpSession session) {

    // 이 페이지 컨트롤러가 작업을 하는 동안 세션에 임시 보관했던 값들은
    // 예를 들어, "DB에 저장한 후" 세션에서 제거한다.

    return String.format("이름2=%s, 나이2=%s, 전화2=%s 를 DB에 저장했음!\n",
        session.getAttribute("name2"),
        session.getAttribute("age2"),
        session.getAttribute("tel2"));
  }

}


