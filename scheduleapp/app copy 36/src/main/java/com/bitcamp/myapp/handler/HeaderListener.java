package com.bitcamp.myapp.handler;

import com.bitcamp.util.ActionListener;
import com.bitcamp.util.BreadcrumbPrompt;

public class HeaderListener implements ActionListener {
  @Override
  public void service(BreadcrumbPrompt prompt) {
    System.out.println("==============================[비트캠프!]====");
  }
}
