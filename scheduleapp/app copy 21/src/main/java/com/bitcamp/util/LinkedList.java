package com.bitcamp.util;

public class LinkedList implements List {

  private class Node {
    Object value;
    Node next;
  }

  private Node head;
  private Node tail;
  private int size;

  @Override
  public boolean add(Object obj) {
    if (obj == null) {
      return false;
    }
    Node node = new Node();
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
    Node cursor = this.head;
    for (int i = 0; i < this.size; i++) {
      arr[i] = cursor.value;
      cursor = cursor.next;
    }
    return arr;
  }

  @Override
  public Object get(int index) {
    if (!isValid(index)) {
      return null;
    }

    Node cursor = this.head;
    for (int i = 0; i < index; i++) {
      cursor = cursor.next;
    }
    return cursor.value;
  }

  @Override
  public boolean remove(Object obj) {
    Node prev = null;
    Node cursor = this.head;

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

  @Override
  public Object remove(int index) {

    if (!isValid(index)) {
      return null;
    }

    Node prev = null;
    Node cursor = this.head;
    for (int i = 0; i < index; i++) {
      prev = cursor;
      cursor = cursor.next;
    }

    Object old = cursor;

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

  @Override
  public int size() {
    return this.size;
  }

  private boolean isValid(int index) {
    return index > -1 && index <= this.size;
  }
  //
  // private int indexOf(Object obj) {
  // Node cursor = this.head;
  // for (int i = 0; i < this.size; i++) {
  // if (cursor.value.equals(obj)) {
  // return i;
  // }
  // }
  // return -1;
  // }

}
