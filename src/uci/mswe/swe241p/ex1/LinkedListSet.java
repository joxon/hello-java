package uci.mswe.swe241p.ex1;

import uci.mswe.swe241p.ex1.LinkedListNode;

/**
 * LinkedListSet
 */
public class LinkedListSet extends Set {
  private LinkedListNode dummyHead;

  public LinkedListSet() {
    this.dummyHead = new LinkedListNode("");
  }

  public boolean add(String word) {
    var node = this.dummyHead;
    while (node.next != null) {
      if (node.word.equals(word)) {
        return false;
      }
      node = node.next;
    }
    node.next = new LinkedListNode(word);
    return true;
  }

  public boolean contains(String word) {
    var node = this.dummyHead;
    while (node.next != null) {
      if (node.word.equals(word)) {
        return true;
      }
      node = node.next;
    }
    return false;
  }

  public int size() {
    int size = 0;
    var node = this.dummyHead;
    while (node.next != null) {
      ++size;
      node = node.next;
    }
    return size;
  }

}