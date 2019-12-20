package com.leetcode;

import java.util.Arrays;

/**
 * _0238ProductOfArrayExceptSelf
 *
 * https://leetcode.com/problems/product-of-array-except-self/
 *
 */
public class _0238ProductOfArrayExceptSelf {

  public static void main(String[] args) {
    int[] nums = {1, 2, 3, 4};
    printArray(Solution.productExceptSelf(nums));
    printArray(Solution.productExceptSelfLR(nums));
    printArray(Solution.productExceptSelfBruteForce(nums));
    printArray(Solution.productExceptSelfWithDivision(nums));
  }

  private static void printArray(int[] arr) {
    System.out.println(Arrays.toString(arr));
  }

  private static class Solution {

    static public int[] productExceptSelf(int[] nums) {
      int len = nums.length;
      int[] ans = new int[len];

      // answer[i] contains the product of all the elements to the left
      // Note: for the element at index '0', there are no elements to the left,
      // so the answer[0] would be 1
      ans[0] = 1;
      for (int i = 1; i < len; i++) {

        // answer[i - 1] already contains the product of elements to the left of 'i - 1'
        // Simply multiplying it with nums[i - 1] would give the product of all
        // elements to the left of index 'i'
        ans[i] = nums[i - 1] * ans[i - 1];
      }

      // R contains the product of all the elements to the right
      // Note: for the element at index 'length - 1', there are no elements to the right,
      // so the R would be 1
      int R = 1;
      for (int i = len - 1; i >= 0; i--) {

        // For the index 'i', R would contain the
        // product of all elements to the right. We update R accordingly
        ans[i] = ans[i] * R;
        R *= nums[i];
      }

      return ans;
    }

    static public int[] productExceptSelfLR(int[] nums) {

      // The length of the input array
      int length = nums.length;

      // The left and right arrays as described in the algorithm
      int[] L = new int[length];
      int[] R = new int[length];

      // Final answer array to be returned
      int[] answer = new int[length];

      // L[i] contains the product of all the elements to the left
      // Note: for the element at index '0', there are no elements to the left,
      // so L[0] would be 1
      L[0] = 1;
      for (int i = 1; i < length; i++) {

        // L[i - 1] already contains the product of elements to the left of 'i - 1'
        // Simply multiplying it with nums[i - 1] would give the product of all
        // elements to the left of index 'i'
        L[i] = nums[i - 1] * L[i - 1];
      }

      // R[i] contains the product of all the elements to the right
      // Note: for the element at index 'length - 1', there are no elements to the right,
      // so the R[length - 1] would be 1
      R[length - 1] = 1;
      for (int i = length - 2; i >= 0; i--) {

        // R[i + 1] already contains the product of elements to the right of 'i + 1'
        // Simply multiplying it with nums[i + 1] would give the product of all
        // elements to the right of index 'i'
        R[i] = nums[i + 1] * R[i + 1];
      }

      // Constructing the answer array
      for (int i = 0; i < length; i++) {
        // For the first element, R[i] would be product except self
        // For the last element of the array, product except self would be L[i]
        // Else, multiple product of all elements to the left and to the right
        answer[i] = L[i] * R[i];
      }

      return answer;
    }

    static public int[] productExceptSelfBruteForce(int[] nums) {

      // Time Limit Exceeded

      if (nums == null || nums.length <= 1) {
        return null;
      }

      int len = nums.length;
      int[] products = new int[len];
      for (int i = 0; i < len; i++) {
        products[i] = 1;
        for (int j = 0; j < len; j++) {
          if (j != i) {
            products[i] *= nums[j];
          }
        }
      }
      return products;
    }

    static public int[] productExceptSelfWithDivision(int[] nums) {

      // Note: Please solve it without division and in O(n).

      if (nums == null || nums.length <= 1) {
        return null;
      }

      int len = nums.length;
      boolean hasOneZero = false;
      boolean hasTwoZero = false;
      int productAll = nums[0];
      for (int i = 1; i < len; i++) {
        int num = nums[i];
        if (num == 0 && hasOneZero) {
          hasTwoZero = true;
          break;
        }
        if (num == 0) {
          hasOneZero = true;
          num = 1;
        }
        productAll *= num;
      }

      int[] products = new int[len];
      if (hasTwoZero) {
        for (int i = 0; i < len; i++) {
          products[i] = 0;
        }
      } else if (hasOneZero) {
        for (int i = 0; i < len; i++) {
          int num = nums[i];
          if (num == 0) {
            products[i] = productAll;
          } else {
            products[i] = 0;
          }
        }
      } else {
        for (int i = 0; i < len; i++) {
          products[i] = productAll / nums[i];
        }
      }
      return products;
    }
  }

}
