package com.bitcamp.util;

import java.util.Scanner;

public class Prompt {
  static Scanner scanner = new java.util.Scanner(System.in);

  public static boolean promptContinue(String str) {

    return str.equalsIgnoreCase("y");
  }

  public static String inputString(String title) {
    System.out.print(title);
    return scanner.nextLine();
  }

  public static int inputInt(String title) {
    System.out.print(title);
    return Integer.parseInt(scanner.nextLine());
  }

  public static void close() {
    scanner.close();
  }

}
