package com.bitcamp.util;

import java.lang.reflect.Array;

public class ArrayList<E> implements List<E> {

  private static final int DEFAULT_SIZE = 100;
  private Object[] list = new Object[DEFAULT_SIZE];
  private int size = 0;

  @Override
  public boolean add(E obj) {
    if (obj == null) {
      return false;
    }
    if (size == list.length) {
      increase();
    }
    list[size++] = obj;
    return true;
  }

  private void increase() {
    Object[] arr = new Object[size << 1];
    for (int i = 0; i < size; i++) {
      arr[i] = list[i];
    }
    list = arr;
  }

  @Override
  public Object[] toArray() {
    Object[] arr = new Object[size];
    for (int i = 0; i < size; i++) {
      arr[i] = list[i];
    }
    return arr;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T[] toArray(T[] arr) {
    T[] values = null;

    if (arr.length < this.size) {
      values = (T[]) Array.newInstance(getClass().getComponentType(), this.size);
    } else {
      values = arr;
    }

    for (int i = 0; i < this.size; i++) {
      values[i] = (T) this.list[i];
    }
    return values;
  }

  @SuppressWarnings("unchecked")
  @Override
  public E get(int index) {
    if (!isValid(index)) {
      return null;
    }
    return (E) list[index];
  }

  @Override
  public boolean remove(E obj) {
    int index = indexOf(obj);
    if (index == -1) {
      return false;
    }
    for (int i = index; i < this.size - 1; i++) {
      this.list[i] = this.list[i + 1];
    }

    this.list[this.size - 1] = null;

    this.size--;

    return true;
  }

  @Override
  public E remove(int index) {
    if (!isValid(index)) {
      return null;
    }

    @SuppressWarnings("unchecked")
    E old = (E) this.list[index];

    for (int i = index; i < this.size - 1; i++) {
      this.list[i] = this.list[i + 1];
    }

    this.list[this.size - 1] = null;

    this.size--;

    return old;
  }

  @Override
  public int size() {
    return this.size;
  }

  private boolean isValid(int index) {
    return index > -1 && index <= this.size;
  }

  private int indexOf(Object obj) {
    for (int i = 0; i < size; i++) {
      if (list[i].equals(obj)) {
        return i;
      }
    }
    return -1;
  }
}
