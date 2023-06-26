package com.bitcamp.util;

public abstract class AbstractList<E> implements List<E> {
  protected int size;

  @Override
  public int size() {
    return this.size;
  }

  @Override
  public Iterator<E> iterator() {
    return new Iterator<>() {
      int cursor;

      @Override
      public boolean hasNext() {
        return cursor < AbstractList.this.size();
      }

      @Override
      public E next() {
        return AbstractList.this.get(cursor++);
      }
    };
  }

  protected boolean isValid(int index) {
    return index > -1 && index <= this.size;
  }

  public boolean empty() {
    if (this.size() == 0) {
      return true;
    } else {
      return false;
    }
  }

}
