package misc;

import java.util.ArrayList;

/**
 * CerealSegments
 *
 * TuSimple OA1 45min
 */
public class CerealSegments {

  public static void main(String[] args) {
    System.out.println(solve(3, 5, new int[] { 2, 5, 4, 6, 8 }));
    System.out.println(solve(2, 3, new int[] { 1, 1, 1 }));
    System.out.println(solve(1, 5, new int[] { 1, 2, 3, 1, 2 }));
  }

  // TOO SLOW!
  public static int solve(int x, int size, int[] arr) {
    var list = new ArrayList<Integer>();

    int len = size - x;
    for (int i = 0; i < len; i++) {
      int min = arr[i];
      for (int j = 1; j < x; j++) {
        min = Math.min(min, arr[i + j]);
      }
      list.add(min);
    }

    int max = 0;
    for (var i : list) {
      max = Math.max(i.intValue(), max);
    }
    return max;
  }
}