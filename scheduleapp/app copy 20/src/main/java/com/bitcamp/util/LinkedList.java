package com.bitcamp.util;

public class LinkedList {

  private class Node {
    Object value;
    Node next;
  }

  private Node head;
  private Node tail;
  private int size;

  public void add(Object obj) {
    Node node = new Node();
    node.value = obj;

    if (this.head == null) {
      this.head = node;
    } else {
      this.tail.next = node;
    }
    tail = node;
    size++;
  }

  public Object[] list() {
    Object[] arr = new Object[this.size];
    Node cursor = this.head;
    for (int i = 0; i < this.size; i++) {
      arr[i] = cursor.value;
      cursor = cursor.next;
    }
    return arr;
  }

  public Object get(Object obj) {
    Node cursor = this.head;
    while (!cursor.value.equals(obj)) {
      cursor = cursor.next;
    }
    return cursor == null ? null : cursor.value;
  }

  public void remove(Object obj) {
    Node prev = null;
    Node cursor = this.head;

    while (!cursor.value.equals(obj)) {
      prev = cursor;
      cursor = cursor.next;
    }

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
  }

  private int indexOf(Object obj) {
    Node cursor = this.head;
    for (int i = 0; i < this.size; i++) {
      if (cursor.value.equals(obj)) {
        return i;
      }
    }
    return -1;
  }

}
