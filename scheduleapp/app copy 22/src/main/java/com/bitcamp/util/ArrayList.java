package com.bitcamp.util;

public class ArrayList implements List {

  private static final int DEFAULT_SIZE = 100;
  private Object[] list = new Object[DEFAULT_SIZE];
  private int length = 0;

  @Override
  public boolean add(Object obj) {
    if (obj == null) {
      return false;
    }
    if (length == list.length) {
      increase();
    }
    list[length++] = obj;
    return true;
  }

  private void increase() {
    Object[] arr = new Object[length << 1];
    for (int i = 0; i < length; i++) {
      arr[i] = list[i];
    }
    list = arr;
  }

  @Override
  public Object[] toArray() {
    Object[] arr = new Object[length];
    for (int i = 0; i < length; i++) {
      arr[i] = list[i];
    }
    return arr;
  }

  @Override
  public Object get(int index) {
    if (!isValid(index)) {
      return null;
    }
    return list[index];
  }

  @Override
  public boolean remove(Object obj) {
    int index = indexOf(obj);
    if (index == -1) {
      return false;
    }
    for (int i = index; i < this.length - 1; i++) {
      this.list[i] = this.list[i + 1];
    }

    this.list[this.length - 1] = null;

    this.length--;

    return true;
  }

  @Override
  public Object remove(int index) {
    if (!isValid(index)) {
      return null;
    }

    Object old = this.list[index];

    for (int i = index; i < this.length - 1; i++) {
      this.list[i] = this.list[i + 1];
    }

    this.list[this.length - 1] = null;

    this.length--;

    return old;
  }

  @Override
  public int size() {
    return this.length;
  }

  private boolean isValid(int index) {
    return index > -1 && index <= this.length;
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
