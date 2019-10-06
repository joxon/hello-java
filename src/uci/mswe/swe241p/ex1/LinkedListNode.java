package uci.mswe.swe241p.ex1;

/**
 * LinkedListNode
 */
public class LinkedListNode {
  String word;
  LinkedListNode next;

  public LinkedListNode(String word) {
    this.word = word;
  }

  public LinkedListNode(String word, LinkedListNode next) {
    this.word = word;
    this.next = next;
  }

  public void append(String word) {
    this.next = new LinkedListNode(word);
  }

  public void append(LinkedListNode node) {
    this.next = node;
  }

  static public void print(LinkedListNode head) {
    LinkedListNode.print(head, 10);
  }

  static public void print(LinkedListNode head, int max) {
    LinkedListNode node = head;
    String s = "";
    while (node != null && max > 0) {
      s += node.word + "->";
      node = node.next;
      --max;
    }
    s += max > 0 ? "null" : "...";
    System.out.println(s);
  }

  public static void main(String[] args) {
    var head = new LinkedListNode("hello");

    var node = head;
    node.append("world");
    node = node.next;
    node.append("你好");
    node = node.next;
    node.append("世界");

    LinkedListNode.print(head);
  }
}