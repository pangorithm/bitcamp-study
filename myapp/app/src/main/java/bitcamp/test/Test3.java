package bitcamp.test;

import bitcamp.test.p1.A;

public class Test3 extends A {
  public static void main(String[] args) {
    A obj1 = new A();
    obj1.v1 = 100; // 접근 불가;
    obj1.v2 = 200; // 접근 불가;
    obj1.v3 = 300; // 접근 불가;
    obj1.v4 = 400;
    obj1.m1(); // 접근 불가; <= 상속 받은 멤버가 아니다!

    m2();

    Test3 obj2 = new Test3();
    obj2.m3();

    obj2.v1 = 100; // 접근 불가;
    obj2.v2 = 200; // 접근 불가;
    obj2.v3 = 300; // 자식 클래스가 상속 받아서 사용하는 멤버!;
    obj2.v4 = 400;
    obj2.m1(); // 자식 클래스가 상속 받아서 사용하는 멤버!
  }

  static void m2() {

  }

  void m3() {

  }
}
