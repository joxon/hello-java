package uci.mswe.swe241p.ex1_set_implementations;

/**
 * LinkedListSet
 */
public class LinkedListSet extends Set {

  private LinkedListNode dummyHead;

  private int size;

  public LinkedListSet() {
    this.dummyHead = new LinkedListNode(""); // Should NOT be null
    this.size = 0;
  }

  public boolean add(String word) {
    var node = this.dummyHead;
    while (node.getNext() != null) {
      if (node.getWord().equals(word)) {
        return false;
      }
      node = node.getNext();
    }
    // now node.next == null, i.e. the last node
    if (node.getWord().equals(word)) {
      return false;
    } else {
      node.setNext(new LinkedListNode(word));
      ++size;
      return true;
    }
  }

  public boolean contains(String word) {
    var node = this.dummyHead;
    while (node != null) {
      if (node.getWord().equals(word)) {
        return true;
      }
      node = node.getNext();
    }
    return false;
  }

  public int size() {
    return size;
  }

  public int sizeTheSlowWay() {
    int size = 0;
    var node = this.dummyHead;
    while (node.getNext() != null) {
      ++size;
      node = node.getNext();
    }
    return size;
  }
}