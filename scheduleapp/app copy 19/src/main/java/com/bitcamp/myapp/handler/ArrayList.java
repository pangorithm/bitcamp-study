package com.bitcamp.myapp.handler;

public class ArrayList {

  private static final int DEFAULT_SIZE = 100;
  private Object[] list = new Object[DEFAULT_SIZE];
  private int length = 0;

  public void add(Object obj) {
    if (length == list.length) {
      increase();
    }
    list[length++] = obj;
  }

  private void increase() {
    Object[] arr = new Object[length << 1];
    for (int i = 0; i < length; i++) {
      arr[i] = list[i];
    }
    list = arr;
  }

  public Object[] list() {
    Object[] arr = new Object[length];
    for (int i = 0; i < length; i++) {
      arr[i] = list[i];
    }
    return arr;
  }

  public Object get(Object obj) {
    int index = indexOf(obj);
    if (index == -1) {
      return null;
    }
    return list[index];
  }

  public void set(Object obj) {
    int index = indexOf(obj);
    if (index == -1) {
      return;
    }
    list[index] = obj;
  }

  public void remove(Object obj) {
    for (int i = indexOf(obj); i < this.length - 1; i++) {
      this.list[i] = this.list[i + 1];
    }

    this.list[this.length - 1] = null;

    this.length--;
  }

  private int indexOf(Object obj) {
    for (int i = 0; i < length; i++) {
      if (list[i].equals(obj)) {
        return i;
      }
    }
    return -1;
  }
}
