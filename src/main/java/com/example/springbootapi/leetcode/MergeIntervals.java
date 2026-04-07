package com.example.springbootapi.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeIntervals {
    /*  
    Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.
    Example 1:

    Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
    Output: [[1,6],[8,10],[15,18]]
    Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].
*/

    public int[][] merge(int[][] intervals) {
        List<int[]> result = new ArrayList();
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        int[] start = intervals[0];

        for (int i = 1; i < intervals.length; i++) {
            int[] current = intervals[i];
            if (start[1] > current[0]) {
                start[1] = Math.max(start[1], current[1]); 
            } else {
                result.add(start);
                start = current; //make the new element start now
            }
        }
        
        result.add(start); // Add the last merged interval
        return result.toArray(new int[result.size()][]); //always convert back --- use arraylist for result, sort using Arrays.sort(object, comparator)
    }

    private void diffSorting() {
        //sorting ascending:
        int[] nums = {5,2,4,1};
        Arrays.sort(nums);

        //sorting descending:
        Integer[] numsDesc = {1,5,11,4}; // 5,1,11,4 seecond 5,11,1,4 third 11,5,1,4 fourth 11,5,4,1
        Arrays.sort(numsDesc, (a,b) -> b-a); //11,5,4,1
        List<Integer> desc = new ArrayList<>();

        //2D array
        int[][] intervals = {{1,3}, {2,6}, {8,10}, {15,18}};
        Arrays.sort(intervals, (a,b) -> a[0] - b[0]); // should sort by 1 - 2 , negative so stays in place, this is descending by value of [0] 

    }

    // Test case
    public static void main(String[] args) {
        MergeIntervals solution = new MergeIntervals();
        int[][] intervals = {{1,3},{2,6},{8,10},{15,18}};
        int[][] result = solution.merge(intervals);
        
        System.out.println("Merged intervals:");
        for (int[] interval : result) {
            System.out.println("[" + interval[0] + "," + interval[1] + "]");
        }
    }
}
