package com.bitcamp.util;

import java.io.InputStream;
import java.util.Scanner;

public class Prompt {

  private Scanner scanner;

  public Prompt() {
    this.scanner = new Scanner(System.in);
  }

  // 다른 입력 도구와 연결한다면
  public Prompt(InputStream in) {
    this.scanner = new Scanner(in);
  }

  public boolean promptContinue(String str) {

    return str.equalsIgnoreCase("y");
  }

  public String inputString(String title, Object... args) {
    System.out.printf(title, args);
    return this.scanner.nextLine();
  }

  public int inputInt(String title, Object... args) {
    String str = inputString(title, args).replaceAll("[^0-9]", "");
    if (str.length() != 0) {
      return Integer.parseInt(str);
    } else {
      System.out.println("올바르지 않은 정수 입력입니다");
      return Integer.MIN_VALUE;
    }
  }

  public void close() {
    this.scanner.close();
  }

}
