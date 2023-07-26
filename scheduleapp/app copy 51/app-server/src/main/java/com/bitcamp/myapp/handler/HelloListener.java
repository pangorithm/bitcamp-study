package com.bitcamp.myapp.handler;

import java.io.IOException;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;

public class HelloListener implements ActionListener {

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    String name = prompt.inputString("이름은? ");
    prompt.printf("%s님 반가워요!\n", name);
  }
}
