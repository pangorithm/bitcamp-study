package com.eomcs.lang.ex99;

//# 키보드 입력 받기 - 여러 개의 데이터를 한 번에 입력하고 쪼개서 읽기
//
public class Exam0250 {
  public static void main(String[] args) {
    java.util.Scanner keyboardScanner = new java.util.Scanner(System.in);

    System.out.print("나이, 이름, 취업여부? ");
    int age = keyboardScanner.nextInt(); // 엔터가 입력 될때까지 값을 받지 않음
    // 스페이스바로도 구별 되는데? -> 잘못된 값을 입력해보면 확인 가능 -> 엔터를 입력해야만 오류 발생

    // 한 개의 토큰을 읽을 때 유용하다.
    String name = keyboardScanner.next();

    boolean working = keyboardScanner.nextBoolean();

    keyboardScanner.close();

    System.out.printf("%d, %s, %b\n", age, name, working);
  }
}
