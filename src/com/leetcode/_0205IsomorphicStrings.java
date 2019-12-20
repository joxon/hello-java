package com.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * _0205IsomorphicStrings
 *
 * https://leetcode.com/problems/isomorphic-strings/
 *
 */
public class _0205IsomorphicStrings {

  public static void main(String[] args) {
    String s = "title";
    String t = "paper";

    System.out.println(Solution.isIsomorphic(s, t));
    System.out.println(Solution.isIsomorphic2(s, t));
  }

  static class Solution {
    static public boolean isIsomorphic(String s, String t) {

      if (s == null || t == null || s.length() != t.length()) {
        return false;
      }

      // You may assume both s and t have the same length.

      Map<Character, Integer> maps = new HashMap<>();
      Map<Character, Integer> mapt = new HashMap<>();

      int len = s.length();
      for (int i = 0; i < len; i++) {
        Character cs = s.charAt(i);
        Character ct = t.charAt(i);

        int lasts = maps.getOrDefault(cs, -1);
        int lastt = mapt.getOrDefault(ct, -1);

        // NullPointerException
        // Integer lasts = maps.get(cs);
        // Integer lastt = maps.get(ct);

        if (lasts != lastt) {
          return false;
        } else {
          maps.put(cs, i);
          mapt.put(ct, i);
        }
      }

      return true;
    }

    static public boolean isIsomorphic2(String s, String t) {

      // https://leetcode.com/problems/isomorphic-strings/discuss/57802/Java-solution-using-HashMap

      if (s == null || t == null)
        return false;
      if (s.length() != t.length())
        return false;

      Map<Character, Integer> mapS = new HashMap<Character, Integer>();
      Map<Character, Integer> mapT = new HashMap<Character, Integer>();

      for (int i = 0; i < s.length(); i++) {
        int indexS = mapS.getOrDefault(s.charAt(i), -1);
        int indexT = mapT.getOrDefault(t.charAt(i), -1);

        if (indexS != indexT) {
          return false;
        }

        mapS.put(s.charAt(i), i);
        mapT.put(t.charAt(i), i);
      }

      return true;
    }
  }


}
