package com.bitcamp.util;

import java.lang.reflect.Array;

public class LinkedList<E> extends AbstractList<E> {

  private class Node<T> {
    T value;
    Node<T> next;
  }

  private Node<E> head;
  private Node<E> tail;

  @Override
  public boolean add(E obj) {
    if (obj == null) {
      return false;
    }
    Node<E> node = new Node<>();
    node.value = obj;

    if (this.head == null) {
      this.head = node;
    } else {
      this.tail.next = node;
    }
    tail = node;
    size++;

    return true;
  }

  @Override
  public Object[] toArray() {
    Object[] arr = new Object[this.size];
    Node<E> cursor = this.head;
    for (int i = 0; i < this.size; i++) {
      arr[i] = cursor.value;
      cursor = cursor.next;
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

    Node<E> cursor = this.head;
    for (int i = 0; i < this.size; i++) {
      values[i] = (T) cursor.value;
      cursor = cursor.next;
    }
    return values;
  }

  @Override
  public E get(int index) {
    if (!isValid(index)) {
      return null;
    }

    Node<E> cursor = this.head;
    for (int i = 0; i < index; i++) {
      cursor = cursor.next;
    }
    return cursor.value;
  }

  @Override
  public boolean remove(Object obj) {
    Node<E> prev = null;
    Node<E> cursor = this.head;

    while (cursor != null) {
      if (cursor.value.equals(obj)) {
        if (prev == null) {
          this.head = cursor.next;
        } else {
          prev.next = cursor.next;
        }

        if (cursor.next == null) {
          tail = prev;
        }

        cursor.value = null;
        cursor.next = null;
        size--;
        return true;
      }
      prev = cursor;
      cursor = cursor.next;
    }
    return false;
  }

  @SuppressWarnings("unchecked")
  @Override
  public E remove(int index) {

    if (!isValid(index)) {
      return null;
    }

    Node<E> prev = null;
    Node<E> cursor = this.head;
    for (int i = 0; i < index; i++) {
      prev = cursor;
      cursor = cursor.next;
    }

    E old = (E) cursor;

    if (prev == null) {
      this.head = cursor.next;
    } else {
      prev.next = cursor.next;
    }

    if (cursor.next == null) {
      tail = prev;
    }

    cursor.value = null;
    cursor.next = null;
    size--;

    return old;
  }

  private int indexOf(E obj) {
    Node<E> cursor = this.head;
    for (int i = 0; i < this.size; i++) {
      if (cursor.value.equals(obj)) {
        return i;
      }
    }
    return -1;
  }

}
