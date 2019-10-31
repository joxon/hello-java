package uci.mswe.swe241p.ex2_sorting_algorithms;

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
 *
 *           Here we use max heap to find the max elements, then move them to
 *           the end
 */
public class HeapSort extends Sort {
  @Override
  void sort(List<String> wordList) {
    final var len = wordList.size();
    if (len <= 1) {
      return;
    }

    // initialize the heap, from bottom to top
    // then wordList[0] is the max element
    // len / 2 - 1: the last node who has subtree on 2nd lowest level
    // O(n)
    for (int i = len / 2 - 1; i >= 0; --i) {
      // O(lg(n))
      makeMaxHeap(wordList, len, i);
    }
    // O(nlg(n))

    // O(n)
    for (int i = len - 1; i >= 0; --i) {
      // move the max element to end
      swap(wordList, 0, i);

      // make heap to find the next max element
      // O(lg(n))
      makeMaxHeap(wordList, i, 0);
    }
    // O(nlg(n))
  }

  // O(lg(n))
  void makeMaxHeap(List<String> wordList, int len, int rootIndex) {
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

      makeMaxHeap(wordList, len, wordLargestIndex);
    }
  }
}