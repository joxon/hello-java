package misc;

import java.util.Arrays;

/**
 * ArraysSort
 */
public class ArraysSort {

  public static void main(final String[] args) {
    final String[] arr = {"abc", "10", "0"}; // alphabetical order

    Arrays.parallelSort(arr); // Java 8+
    // Arrays.sort(arr);

    // final String deepString = Arrays.deepToString(arr);
    final String string = Arrays.toString(arr);

    // System.out.println(deepString);
    System.out.println(string);
  }
}
