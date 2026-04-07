package com.example.springbootapi.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class pccfinal {
    /*
    Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
    An input string is valid if:

    Open brackets must be closed by the same type of brackets.
    Open brackets must be closed in the correct order.
    Every close bracket has a corresponding open bracket of the same type.


    example:Input: s = "()[]{}"
    Output: true

    */

    public boolean isValid(String s) {
        HashMap<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');
        Stack<Character> stack = new Stack<>();

        for (char c: s.toCharArray()) {
        // ( exists, add this to the stack,
        // if ) exist, we are good. string ( )
            if (map.containsKey(c)) {
                if (stack.isEmpty()) {
                    return false;
                }
                Character elementPopped = stack.pop();
                if (map.get(c) != elementPopped) {
                    return false;
                }

            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }

    public boolean isPalindrome(String s) {
        if (s.isEmpty()) {
            return true;
        }
        int start = 0;
        int end = s.length() -1;

        while (start  < end) {
            if (s.charAt(start) == s.charAt(end)) {
                start += 1;
                end -=1;
            } else {
                return false;
            }
        }

        return true;
    }

    public int longestSubString(String s) {
        //'abbcs' --> longest is: 3 bcs;
        int left = 0;
        int maxLength = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (map.containsKey(c) && map.get(c) >= left) {
                left = map.get(c) + 1;
            }
            map.put(c, right);

            int current = right - left + 1;
            if (current > maxLength) {
                maxLength = current;
            }
        }

        return maxLength;
    }

    public int[] twoSum(int[] nums, int target) {
        /*
        Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
        You may assume that each input would have exactly one solution, and you may not use the same element twice.
        You can return the answer in any order.
        
        Example 1:

        Input: nums = [2,7,11,15], target = 9 - map.get(i) [7] key ->2 aka value is stored as key, index is stored as value 
        Output: [0,1]
        Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
        */

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i=0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] {map.get(complement), i};
            }
            
            map.put(nums[i], i);
        }
        
        throw new IllegalArgumentException("No two numbers found that sum to target");
    }

        /*  
        Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.
        Example 1:

        Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
        Output: [[1,6],[8,10],[15,18]]
        Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].
        */

    public List<int[]> merge(int[][] intervals) {
        List<int[]> result = new ArrayList();
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        int[] start = intervals[0];

        for(int index = 1; index < intervals.length; index++) {
            int[] current = intervals[index];
            if (start[1] > current[0]) {
                start[1] = Math.max(start[1], current[1]); 
            } else {
                result.add(start);
                start = current;
            }
        }

        result.add(start); //add last pairs
        return result;
    }


    /*
    
    Given a string s, find the first non-repeating character in it and return its index. If it does not exist, return -1.

    Example 1:
    Input: s = "leetcode"
    Output: 0
    Explanation:
    The character 'l' at index 0 is the first character that does not occur at any other index.
    */
       public int firstUniqChar(String s) {
        //return first index of a character that DOES NOT REPEAT... for instance MAGA -> 2 G doesnt have repeats.

        HashMap<Character, Integer> map = new HashMap<>();

        //add all characters in stringg with k,v pair (character, counter)
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        //loop thru each k,v in map
        for (int i = 0; i < s.length(); i++) {
            if (map.get(s.charAt(i)) == 1) {
                return i;
            }
        }
        
        return -1;
    }
    
}
