package com.bitcamp.myapp.handler;

import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;

public class HelloListener implements ActionListener {
  @Override
  public void service(BreadcrumbPrompt prompt) {
    String name = prompt.inputString("이름은? ");
    System.out.printf("%s님 반가워요!\n", name);
  }
}
