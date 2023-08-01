package com.bitcamp.util;

import java.util.ArrayList;

public class Menu {
  private String path; // 메뉴를 식별하는 경로
  private String title; // 메뉴의 제목
  private ArrayList<ActionListener> listeners = new ArrayList<>();

  public Menu(String path, String title) {
    this.path = path;
    this.title = title;
  }

  public Menu(String path, String title, ActionListener listener) {
    this(path, title);
    this.addActionListener(listener);
  }

  public void addActionListener(ActionListener listener) {
    listeners.add(listener);
  }

  public void removeActionListener(ActionListener listener) {
    listeners.remove(listener);
  }

  public String getTitle() {
    return title;
  }

  public void execute(BreadcrumbPrompt prompt) {
    try {
      String[] values = this.path.split("[?]");
      prompt.setAttribute("menuPath", values[0]);

      if (values.length > 1) {
        String[] params = values[1].split("&");

        for (String param : params) {
          String[] kv = param.split("=");
          prompt.setAttribute(kv[0], kv[1]);
        }
      }

      for (int i = 0; i < listeners.size(); i++) {
        ActionListener listener = listeners.get(i);
        listener.service(prompt);
      }

    } catch (Exception e) {
      prompt.clear();
      prompt.println(e.getMessage());

    } finally {
      try {
        prompt.end();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
