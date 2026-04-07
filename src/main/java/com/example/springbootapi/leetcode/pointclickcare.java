package com.example.springbootapi.leetcode;

import java.util.HashMap;
import java.util.Stack;

public class pointclickcare {

        public int longestSubString(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        //'abbbcdefg' --> longest is: 6 bcdefg;.

        HashMap<Character, Integer> result = new HashMap<>();
        int maxLength = 0;
        int left = 0;

        for (int right = 0; right < input.length(); right++) {
            char c = input.charAt(right);
            if (result.containsKey(c) && result.get(c) >= left) {
                left = result.get(c) + 1;
            }

            result.put(c, right);
            //[a,0]
            //[b,1] -> [b,2] -> [b,3]
            //[c,4]
            //[d,5]
            //[e,6]
            //[f,7]
            int currentLength = right - left  + 1;
            if (currentLength > maxLength) {
                maxLength = currentLength;
            }
        }

        return maxLength;
    }

    public boolean isPalindrome(String s) {
        if (s == null) {
            return false;
        }

        if (s.isEmpty()) {
            return true;
        }

        int start = 0;
        int end = s.length()-1;

        while (start < end) {
            if (s.charAt(start) == s.charAt(end)) {
                start = start + 1;
                end = end - 1;
            } else {
                return false;
            }
        }

        return true;
    }


    //stackk shit

    public boolean isValid(String s) {
        //The Mission: Write a function that takes a string s containing (, ), [, ], {, } and returns true if it is balanced. 
        Stack<Character> stack = new Stack<>();

        HashMap<Character, Character> tinku = new HashMap<>();
        tinku.put(')', '(');
        tinku.put(']', '[');
        tinku.put('}', '{');

        for (char c: s.toCharArray()) {
            if (tinku.containsKey(c)) {
                if (stack.isEmpty()) {
                    return false;
                }

              char top = stack.pop();
              if (top != tinku.get(c))  {
                return false;
              }
            } else {
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }
}
