package bitcamp.util;

import java.io.InputStream;
import java.util.Scanner;

public class Prompt {

  // InputStream keyboard = System.in;
  // Scanner scanner = new java.util.Scanner(keyboard);
  private static Scanner scanner;

  // defualt constructor 정의
  public Prompt() {
    scanner = new Scanner(System.in);
  }

  // 다른 입력 도구를 이용한다면
  public Prompt(InputStream in) {
    scanner = new Scanner(in);
  }

  public static String inputString(String title, Object... args) {
    System.out.printf(title, args);
    return scanner.nextLine();
  }

  public static int inputInt(String title, Object... args) {
    return Integer.parseInt(inputString(title, args));
  }

  public void close() {
    this.scanner.close();
  }
}
