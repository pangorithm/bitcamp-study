package bitcamp.util;

public class LinkedList implements List {

  Node head;
  Node tail;
  int size;

  public static void main(String[] args) {
    LinkedList list = new LinkedList();
    list.add(Integer.valueOf(100)); // index: 0
    list.add(Integer.valueOf(200)); // index: 1
    list.add(Integer.valueOf(300)); // index: 2
    list.add(Integer.valueOf(400)); // index: 3
    list.add(Integer.valueOf(500)); // index: 4

    print(list);
    //
    // System.out.println(list.retrieve(100));
    // System.out.println(list.retrieve(300));
    // System.out.println(list.retrieve(500));
    // System.out.println(list.retrieve(600));

    // System.out.print(list.remove(Integer.valueOf(300)));
    // print(list);
    // System.out.print(list.remove(Integer.valueOf(500)));
    // print(list);
    // System.out.print(list.remove(Integer.valueOf(100)));
    // print(list);
    // System.out.print(list.remove(Integer.valueOf(200)));
    // print(list);
    // System.out.print(list.remove(Integer.valueOf(400)));
    // print(list);
    // System.out.print(list.remove(Integer.valueOf(600)));
    // print(list);

    System.out.println(list.remove(2));
    System.out.println(list.remove(3));
    System.out.println(list.remove(0));
    System.out.println(list.remove(0));
    System.out.println(list.remove(0));
    //
    // list.add(Integer.valueOf(1000));
    // list.add(Integer.valueOf(2000));

    print(list);

  }

  static void print(LinkedList list) {
    Object[] arr = list.toArray();
    System.out.print(" list: ");
    for (Object obj : arr) {
      System.out.print(obj);
      System.out.print(", ");
    }
    System.out.println();
  }

  @Override
  public boolean add(Object value) {
    // 1. 새 노드를 생성한다.
    Node node = new Node();

    // 2. 새 노드에 값 저장
    node.value = value;

    // 3. 리스트의 마지막 노드에 새 노드를 연결
    if (this.head == null) {
      this.head = node;
    } else if (this.tail != null) {
      this.tail.next = node;
    }
    this.tail = node;
    this.size++;
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
    if (isValid(index)) {
      return null;
    }
    Node cursor = this.head;

    for (int i = 0; i < index; i++) {
      cursor = cursor.next;
    }

    return cursor.value;
  }

  @Override
  public boolean remove(Object value) {
    Node prev = null;
    Node cursor = this.head;

    while (cursor != null) {
      if (cursor.value.equals(value)) {
        if (prev == null) {
          this.head = cursor.next;
        } else {
          prev.next = cursor.next;
        }

        if (cursor.next == null) {
          this.tail = prev;
        }
        this.size--;
        cursor.next = null;
        cursor.value = null;
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

    // 삭제하려는 값이 있는 노드까지 이동한다.
    Node prev = null;
    Node cursor = this.head;

    for (int i = 0; i < index; i++) {
      prev = cursor; // 다음 노드로 이동하기 전에 현재 커서가 가리키는 노드를 prev에 보관한다.
      cursor = cursor.next; // 커서를 다음 노드로 이동시킨다.
    }

    // 삭제할 값을 리턴할 수 있도록 보관한다.
    Object old = cursor.value;

    if (prev == null) {
      this.head = cursor.next;
    } else {
      prev.next = cursor.next; // 현재 커서의 다음 노드를 현재 커서의 이전 노드와 연결한다.
    }

    if (cursor.next == null) {
      this.tail = prev;
    }
    this.size--;
    cursor.next = null;
    cursor.value = null;
    return old;
  }

  @Override
  public int size() {
    return this.size;
  }

  private boolean isValid(int index) {
    return (index >= 0 || index < this.size);
  }

  static class Node {
    Object value;
    Node next;
  }

}
