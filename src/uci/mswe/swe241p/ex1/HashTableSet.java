package uci.mswe.swe241p.ex1;

import uci.mswe.swe241p.ex1.LinkedListNode;

/**
 * HashTableSet
 */
public class HashTableSet extends Set {
  private final int DEFAULT_MAX_SIZE = 10000;

  private final double MAX_LOAD_FACTOR = 0.7;

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
    return Math.abs(word.hashCode() % maxSize);
  }

  public boolean add(String word) {
    int index = getIndex(word);
    LinkedListNode node = table[index];

    while (node != null) {
      if (node.word.equals(word)) {
        return false;
      }
      node = node.next;
    }

    // Insert key to the head of chain
    var newNode = new LinkedListNode(word);
    newNode.next = table[index];
    table[index] = newNode;
    ++size;

    /**
     * If current load factor exceeds the limit then double the size of table
     */
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
          add(oldNode.word);
          oldNode = oldNode.next;
        }
      }
    }

    return true;
  }

  public boolean contains(String word) {
    var node = table[getIndex(word)];

    while (node != null) {
      if (node.word.equals(word)) {
        return true;
      }
      node = node.next;
    }

    return false;
  }

  // Method to remove a given word
  public boolean remove(String word) {
    // Apply hash function to find index for given word
    int index = getIndex(word);

    // Get head of chain
    LinkedListNode node = table[index];

    // Search for word in its chain
    LinkedListNode prev = null;
    while (node != null) {
      // If word found
      if (node.word.equals(word))
        break;

      // Else keep moving in chain
      prev = node;
      node = node.next;
    }

    // If word was not there
    if (node == null)
      return false;

    // Reduce size
    --size;

    // Remove word
    if (prev != null)
      prev.next = node.next;
    else
      table[index] = node.next;

    return true;
  }

  public static void main(String[] args) {
    var set = new HashTableSet();
    set.add("hello");
    set.add("world");
    set.add("你好");
    set.add("世界");
    System.out.println(set.size());
    System.out.println(set.contains("hello"));
    System.out.println(set.remove("hello"));
    System.out.println(set.contains("hello"));
    System.out.println(set.remove("hello"));
    System.out.println(set.size());
  }
}
