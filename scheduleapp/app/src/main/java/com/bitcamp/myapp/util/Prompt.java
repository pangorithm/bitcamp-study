package com.bitcamp.myapp.util;

public class Prompt {

  public static boolean promptContinue(String str) {

    return str.equalsIgnoreCase("y");
  }

}
