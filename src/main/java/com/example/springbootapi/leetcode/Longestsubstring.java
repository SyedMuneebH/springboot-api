package com.example.springbootapi.leetcode;

import java.util.HashMap;
import java.util.Set;

/**
 * LeetCode Problem: Longest Substring Without Repeating Characters (Problem #3)
 * 
 * Given a string s, find the length of the longest substring without repeating characters.
 */
public class Longestsubstring {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        HashMap<Character, Integer> map = new HashMap<>();
        int maxLength = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right); // now we get first indices a, 
            if (map.containsKey(c) && map.get(c) >= left) { //does a exist in map, and get indice value of firtst letter, compare that with left
            // Only move left if duplicate is within current window
            left = map.get(c) + 1;
            }

            map.put(c, right);
            int currentLength = right - left + 1;
            if (currentLength > maxLength) {
            maxLength = currentLength;
            }
        }

    return maxLength;
    }


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
}

