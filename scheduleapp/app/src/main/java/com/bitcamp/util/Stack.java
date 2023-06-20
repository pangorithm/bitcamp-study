package com.bitcamp.util;

public class Stack extends LinkedList {
  public Object push(Object value) {
    this.add(value);
    return value;
  }

  public Object pop() {
    if (this.empty()) {
      return null;
    }
    return remove(this.size() - 1);
  }

  public Object peek() {
    if (this.empty()) {
      return null;
    }
    return this.get(this.size() - 1);
  }


}
