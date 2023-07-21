// 파라미터 타입 - 상속 관계: 기본
package com.eomcs.generic.ex02;

public class Exam0111 {

  static class A {
  }
  static class B1 extends A {
  }
  static class B2 extends A {
  }
  static class C extends B1 {
  }
  /*
   * Object | A / \ B1 B2 | C
   */

  // T 자리에는 B1 계열의 객체만 전달할 수 있다.
  static class Box<T> {
    void set(T obj) {}
  }

  public static void main(String[] args) {
    Box<B1> box1;
    // box1 = new Box<Object>(); // 컴파일 오류!
    // box1 = new Box<A>(); // 컴파일 오류!
    box1 = new Box<B1>();
    // box1 = new Box<C>(); // 컴파일 오류!
  }
}


