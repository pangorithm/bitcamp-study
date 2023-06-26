package com.bitcamp.util;

public interface List<E> {
  boolean add(E value);

  E get(int index);

  Object[] toArray();

  <T> T[] toArray(T[] arr);

  E remove(int index);

  boolean remove(E value);

  int size();

  Iterator<E> iterator();

}
