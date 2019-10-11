package uci.mswe.swe241p.ex2;

import java.util.List;

/**
 * HeapSort
 *
 * @implNote A Binary Heap is a Complete Binary Tree where items are stored in a
 *           special order such that value in a parent node is greater(or
 *           smaller) than the values in its two children nodes. The former is
 *           called as max heap and the latter is called min heap.
 *
 *           The heap can be represented by binary tree or array.
 */
public class HeapSort extends Sort {
  @Override
  void sort(List<String> wordList) {
    final var len = wordList.size();
    if (len <= 1) {
      return;
    }

    // Build heap (rearrange array)
    for (int i = len / 2 - 1; i >= 0; i--)
      makeHeap(wordList, len, i);

    // One by one extract an element from heap
    for (int i = len - 1; i >= 0; i--) {
      // Move current root to end
      swap(wordList, 0, i);

      // call max heapify on the reduced heap
      makeHeap(wordList, i, 0);
    }
  }

  int recurCount = 1;

  void makeHeap(List<String> wordList, int len, int rootIndex) {
    if (recurCount % 10 == 0) {
      System.out.println("Recursion " + (recurCount++));
    }

    // assume the root word is the largest
    var wordLargestIndex = rootIndex;
    var wordLargest = wordList.get(wordLargestIndex);

    // left child of the root
    var left = 2 * rootIndex + 1;
    var wordLeft = left < len ? wordList.get(left) : null;

    // right child of the root
    var right = 2 * rootIndex + 2;
    var wordRight = right < len ? wordList.get(right) : null;

    // If left child is larger than root
    if (left < len && wordLeft.compareTo(wordLargest) > 0) {
      wordLargestIndex = left;
      wordLargest = wordList.get(wordLargestIndex);
    }

    // If right child is the largest
    if (right < len && wordRight.compareTo(wordLargest) > 0) {
      wordLargestIndex = right;
      wordLargest = wordList.get(wordLargestIndex);
    }

    // If largest is not root
    if (wordLargestIndex != rootIndex) {
      swap(wordList, wordLargestIndex, rootIndex);

      makeHeap(wordList, len, wordLargestIndex);
    }
  }
}