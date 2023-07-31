package com.bitcamp.myapp.handler;

import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;

public class FooterListener implements ActionListener {
  @Override
  public void service(BreadcrumbPrompt prompt) {
    prompt.println("Copyright\u00a9 by ncc 7기----------------------------");
  }
}
