package uci.mswe.swe241p.ex2;

import java.util.List;

/**
 * MergeSort
 */
public class MergeSort extends Sort {
  @Override
  void sort(List<String> wordList) {
    final var len = wordList.size();
    if (len <= 1) {
      return;
    }

    msort(wordList, 0, len - 1);
  }

  void msort(List<String> wordList, int left, int right) {
    if (left < right) {
      var mid = (left + right) / 2;
      msort(wordList, left, mid);
      msort(wordList, mid + 1, right);
      merge(wordList, left, mid, right);
    }
  }

  void merge(List<String> wordList, int left, int mid, int right) {
    // copy two subarrays
    var lenLeft = mid - left + 1;
    var lenRight = right - mid;

    var wordsLeft = new String[lenLeft];
    var wordsRight = new String[lenRight];

    for (int i = 0; i < lenLeft; ++i) {
      wordsLeft[i] = wordList.get(left + i);
    }
    for (int i = 0; i < lenRight; ++i) {
      wordsRight[i] = wordList.get(mid + 1 + i);
    }

    // merge two subarrays
    int wordsLeftIndex = 0;
    int wordsRightIndex = 0;
    int wordListIndex = left;
    while (wordsLeftIndex < lenLeft && wordsRightIndex < lenRight) {
      var wordLeft = wordsLeft[wordsLeftIndex];
      var wordRight = wordsRight[wordsRightIndex];

      var wordLeftIsSmaller = wordLeft.compareTo(wordRight) <= 0;
      var wordSmaller = wordLeftIsSmaller ? wordLeft : wordRight;
      if (wordLeftIsSmaller) {
        ++wordsLeftIndex;
      } else {
        ++wordsRightIndex;
      }

      wordList.set(wordListIndex++, wordSmaller);
    }

    // check if there are remaining words in subarrays
    while (wordsLeftIndex < lenLeft) {
      wordList.set(wordListIndex++, wordsLeft[wordsLeftIndex++]);
    }
    while (wordsRightIndex < lenRight) {
      wordList.set(wordListIndex++, wordsRight[wordsRightIndex++]);
    }
  }
}