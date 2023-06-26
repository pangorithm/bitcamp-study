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


  @Override
  public Iterator<E> iterator() {
    return new Iterator<>() {
      int cursor;

      @Override
      public boolean hasNext() {
        return cursor < Stack.this.size();
      }

      @Override
      public E next() {
        return Stack.this.pop();
      }
    };
  }
}
