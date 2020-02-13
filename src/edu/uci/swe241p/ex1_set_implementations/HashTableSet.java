package edu.uci.swe241p.ex1_set_implementations;

/**
 * HashTableSet
 */
public class HashTableSet extends Set {

  private final int DEFAULT_MAX_SIZE = 10000;

  // Actually we have 7105 unique words in the book
  private final double MAX_LOAD_FACTOR = 0.8;

  private LinkedListNode[] table;

  private int size;

  private int maxSize;

  public HashTableSet(int maxSize) {
    assert maxSize > 0;
    this.maxSize = maxSize;
    table = new LinkedListNode[maxSize];
    for (int i = 0; i < maxSize; ++i) {
      table[i] = null;
    }
  }

  public HashTableSet() {
    this.maxSize = DEFAULT_MAX_SIZE;
    table = new LinkedListNode[maxSize];
    for (int i = 0; i < maxSize; ++i) {
      table[i] = null;
    }
  }

  public int size() {
    return size;
  }

  private int getIndex(String word) {
    /**
     * Modulus can be negative using % operator
     *
     * because the sign same as Dividend
     *
     * Solution 1: (a % b + b) % b
     *
     * Solution 2: Math.floorMod() # sign same as Divisor
     */
    return Math.floorMod(word.hashCode(), maxSize);
  }

  public boolean add(String word) {
    int index = getIndex(word);
    LinkedListNode node = table[index];

    while (node != null) {
      if (node.getWord().equals(word)) {
        return false;
      }
      node = node.getNext();
    }

    // Insert key to the head of chain
    var newNode = new LinkedListNode(word);
    newNode.setNext(table[index]);
    table[index] = newNode;
    ++size;

    // If current load factor exceeds the limit then double the size of table
    if ((1.0 * size) / maxSize >= MAX_LOAD_FACTOR) {
      var oldTable = this.table;
      var newTable = new LinkedListNode[2 * maxSize];
      this.table = newTable;
      this.maxSize *= 2;
      this.size = 0;

      for (int i = 0; i < maxSize; ++i) {
        table[i] = null;
      }

      for (LinkedListNode oldNode : oldTable) {
        while (oldNode != null) {
          add(oldNode.getWord());
          oldNode = oldNode.getNext();
        }
      }
    }

    return true;
  }

  public boolean contains(String word) {
    var node = table[getIndex(word)];

    while (node != null) {
      if (node.getWord().equals(word)) {
        return true;
      }
      node = node.getNext();
    }

    return false;
  }

  public boolean remove(String word) {
    int index = getIndex(word);
    LinkedListNode node = table[index];
    LinkedListNode prev = null;

    while (node != null && !node.getWord().equals(word)) {
      prev = node;
      node = node.getNext();
    }

    if (node == null) {
      return false;
    } else {
      --size;

      if (prev != null) {
        prev.setNext(node.getNext());
      } else {
        table[index] = node.getNext();
      }

      return true;
    }
  }

  public static void main(String[] args) {
    var set = new HashTableSet();
    System.out.println(set.add("hello"));
    System.out.println(set.add("world"));
    System.out.println(set.add("你好"));
    System.out.println(set.add("世界"));
    System.out.println(set.size());
    System.out.println(set.contains("hello"));
    System.out.println(set.remove("hello"));
    System.out.println(set.contains("hello"));
    System.out.println(set.remove("hello"));
    System.out.println(set.size());
  }
}
