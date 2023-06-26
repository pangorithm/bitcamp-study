package com.bitcamp.util;

public class Stack<E> extends LinkedList<E> {
  public E push(E value) {
    this.add(value);
    return value;
  }

  public E pop() {
    if (this.empty()) {
      return null;
    }
    return remove(this.size() - 1);
  }

  public E peek() {
    if (this.empty()) {
      return null;
    }
    return this.get(this.size() - 1);
  }


}
