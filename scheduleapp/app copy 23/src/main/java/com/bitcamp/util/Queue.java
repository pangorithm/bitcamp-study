package com.bitcamp.util;

public class Queue extends LinkedList {

  public boolean offer(Object value) {
    return this.add(value);
  }

  public Object poll() {
    if (this.empty()) {
      return null;
    }
    return this.remove(0);
  }


}
