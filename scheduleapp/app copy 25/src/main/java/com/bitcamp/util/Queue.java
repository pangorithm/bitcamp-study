package com.bitcamp.util;

public class Queue<E> extends LinkedList<E> {

  public boolean offer(E value) {
    return this.add(value);
  }

  public E poll() {
    if (this.empty()) {
      return null;
    }
    return this.remove(0);
  }

  @Override
  public Iterator<E> iterator() {
    return new Iterator<>() {
      int cursor;

      @Override
      public boolean hasNext() {
        return cursor < Queue.this.size();
      }

      @Override
      public E next() {
        return Queue.this.poll();
      }
    };
  }

}
