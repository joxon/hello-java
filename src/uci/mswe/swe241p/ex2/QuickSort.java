package uci.mswe.swe241p.ex2;

import java.util.List;

/**
 * QuickSort
 */
public class QuickSort extends Sort {
  @Override
  void sort(List<String> wordList) {
    final var len = wordList.size();
    if (len <= 1) {
      return;
    }

    qsort(wordList, 0, len - 1);
  }

  // int recurCount = 1;

  void qsort(List<String> wordList, int left, int right) {
    // System.out.println("Recursion " + (recurCount++));

    if (left < right) {
      int privotNewIndex = partition(wordList, left, right);
      qsort(wordList, left, privotNewIndex - 1);
      qsort(wordList, privotNewIndex + 1, right);
    }
  }

  int partition(List<String> wordList, int left, int right) {
    // always take the rightmost element as pivot?
    var pivotOldIndex = right;
    var pivotValue = wordList.get(right);
    while (left < right) {
      // find the leftmost word that is greater than pivot
      while (left < right && wordList.get(left).compareTo(pivotValue) <= 0) {
        ++left;
      }
      // find the rightmost word that is smaller than pivot
      while (left < right && wordList.get(right).compareTo(pivotValue) >= 0) {
        --right;
      }
      // move the smaller to left, and the greater to right
      if (left < right) {
        swap(wordList, left, right);
      }
    }
    // now left is the new index of the pivot
    // so move the pivot to its final place
    wordList.set(pivotOldIndex, wordList.get(left));
    wordList.set(left, pivotValue);
    return left;
  }
}