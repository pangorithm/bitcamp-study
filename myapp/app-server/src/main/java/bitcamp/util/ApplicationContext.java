package bitcamp.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import bitcamp.myapp.config.AppConfig;

public class ApplicationContext {
  Map<String, Object> beanContainer = new HashMap<>();

  public ApplicationContext(Class<?> configClass) throws Exception {
    // 클래스의 생성자를 알아낸다
    Constructor<?> constructor = configClass.getConstructor();

    // 기본 생성자를 이용하여 객체를 생성한다.
    Object obj = constructor.newInstance();

    // 해당 클래스에 정의된 메서드 목록만 가져온다.
    Method[] methods = configClass.getDeclaredMethods();
    for (Method m : methods) {
      if (m.getReturnType() == void.class) {
        continue;
      }
      // 메서드 중에서 리턴 값이 있는 메서드만 호출한다.
      Object returnValue = m.invoke(obj);

      // 리턴값을 메서드 이름으로 빈 컨테이너에 저장한다.
      beanContainer.put(m.getName(), returnValue);
    }
  }

  public Object getBean(String name) {
    return beanContainer.get(name);
  }

  public String[] getBeanNames() {
    return beanContainer.keySet().toArray(new String[0]);
  }

  public static void main(String[] args) throws Exception {
    ApplicationContext applicationContext = new ApplicationContext(AppConfig.class);
  }
}
