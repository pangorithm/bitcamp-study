package com.bitcamp.util;

import java.util.ArrayList;

public class ThreadPool implements ResourcePool<ManagedThread> {

  ArrayList<ManagedThread> list = new ArrayList<>();

  @Override
  public ManagedThread getResource() {
    ManagedThread t = null;

    if (list.size() == 0) {
      t = new ManagedThread(this);

      t.start();

      try {
        Thread.sleep(100);
      } catch (Exception e) {
      }
      return t;

    }
    t = list.remove(0);
    return t;
  }

  @Override
  public void returnResource(ManagedThread resource) {
    list.add(resource);
  }
}
