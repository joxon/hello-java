package uci.mswe.swe241p.ex2;

import java.util.List;

/**
 * InsertionSort
 */
public class InsertionSort extends Sort {
  @Override
  void sort(List<String> wordList) {
    final var len = wordList.size();
    if (len <= 1) {
      return;
    }

    // start at the 2nd element
    for (var i = 1; i < len; ++i) {
      if (i % 10000 == 0) {
        System.out.println("Iteration " + i + "/" + len);
      }

      var word = wordList.get(i);
      var j = i - 1;
      // then scan LARGER elements before it and move them to larger indices
      for (; j >= 0 && wordList.get(j).compareTo(word) > 0; --j) {
        wordList.set(j + 1, wordList.get(j));
      }
      // move the word to a smaller index
      wordList.set(j + 1, word);
      // why is it called Insertion?
      // it's like you move the smaller elements to smaller indices
      // by inserting them before the larger ones
      // and larger ones get moved forward to larger indices one by one
    }
  }
}