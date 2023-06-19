package bitcamp.util;

public class LinkedList {

  Node head;
  Node tail;
  int size;

  public static void main(String[] args) {
    LinkedList list = new LinkedList();
    list.add(100);
    list.add(200);
    list.add(300);
    list.add(400);
    list.add(500);

    print(list);
    //
    // System.out.println(list.retrieve(100));
    // System.out.println(list.retrieve(300));
    // System.out.println(list.retrieve(500));
    // System.out.println(list.retrieve(600));

    System.out.print(list.remove(300));
    print(list);
    System.out.print(list.remove(500));
    print(list);
    System.out.print(list.remove(100));
    print(list);
    System.out.print(list.remove(200));
    print(list);
    System.out.print(list.remove(400));
    print(list);
    System.out.print(list.remove(600));
    print(list);

    list.add(700);
    print(list);

  }

  static void print(LinkedList list) {
    Object[] arr = list.getList();
    System.out.print(" list: ");
    for (Object obj : arr) {
      System.out.print(obj);
      System.out.print(", ");
    }
    System.out.println();
  }

  public void add(Object value) {
    // 1. 새 노드를 생성한다.
    Node node = new Node();

    // 2. 새 노드에 값 저장
    node.value = value;

    // 3. 리스트의 마지막 노드에 새 노드를 연결
    if (head == null) {
      head = node;
    } else if (this.tail != null) {
      this.tail.next = node;
    }
    this.tail = node;
    this.size++;
  }

  public Object[] getList() {
    Object[] arr = new Object[this.size];

    Node cursor = this.head;
    for (int i = 0; i < this.size; i++) {
      arr[i] = cursor.value;
      cursor = cursor.next;
    }
    return arr;
  }

  public Object retrieve(Object value) {
    Node cursor = this.head;

    while (cursor != null) {
      if (cursor.value.equals(value)) {
        return cursor.value;
      }
      cursor = cursor.next;
    }

    return null;
  }

  public boolean remove(Object value) {
    Node prev = null;
    Node cursor = this.head;

    while (cursor != null) {
      if (cursor.value.equals(value)) {
        // 삭제할 노드가 시작 노드라면
        if (prev == null) {
          head = cursor.next;
        } else {
          // 다음 노드의 주소를 이전 노드에 저장한다.
          prev.next = cursor.next;
        }

        // 삭제할 노드가 끝 노드라면
        if (cursor.next == null) {
          tail = prev;
        }

        size--;

        // 가비지 객체를 초기화시켜서 가비지가 인스턴스를 가리키지 않도록 한다.
        cursor.next = null;
        cursor.value = null;
        return true;
      }
      // 현재 커서가 가리키는 노드를 prev에 보관한다.
      prev = cursor;

      // 현재 커서를 다음 노드로 이동한다.
      cursor = cursor.next;
    }
    return false;
  }
}
