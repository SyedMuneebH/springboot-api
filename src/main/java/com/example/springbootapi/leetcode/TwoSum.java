package com.example.springbootapi.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode Problem: Two Sum (Problem #1)
 * 
 * Given an array of integers nums and an integer target, return the indices of the two numbers
 * that add up to target. You may assume each input has exactly one solution, and you cannot
 * use the same element twice.
 */
public class TwoSum {

    /**
     * Solves the two sum problem using a HashMap approach.
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     * 
     * @param nums the array of integers
     * @param target the target sum
     * @return an array of two indices [i, j] where nums[i] + nums[j] == target
     * @throws IllegalArgumentException if no two numbers sum to target
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
        
            // Check if complement exists in map
            if (map.containsKey(complement)) {
                return new int[] {map.get(complement), i};
            }
            
            // Add current number and its index to map
            map.put(nums[i], i);
        }
        
        throw new IllegalArgumentException("No two numbers found that sum to target");
    }
}
