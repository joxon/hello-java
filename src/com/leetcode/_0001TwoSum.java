package com.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * _0001TwoSum
 */
public class _0001TwoSum {

  public static void main(String[] args) {
    int[] nums = {2, 7, 11, 15};
    int target = 9;
    System.out.println(Arrays.toString(Solution.twoSum(nums, target)));
  }

  static class Solution {
    static public int[] twoSum(int[] nums, int target) {
      if (nums == null || nums.length < 2) {
        return new int[0];
      }

      Map<Integer, Integer> map = new HashMap<>();
      map.put(nums[0], 0);

      int len = nums.length;
      for (int i = 1; i < len; i++) {
        int num = nums[i];
        int other = target - num;
        if (map.containsKey(other)) {
          return new int[] {map.get(other), i};
        } else {
          map.put(num, i);
        }
      }

      return new int[0];
    }
  }
}
