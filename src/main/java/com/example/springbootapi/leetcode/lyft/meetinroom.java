package com.example.springbootapi.leetcode.lyft;

import java.util.Arrays;
import java.util.PriorityQueue;

public class meetinroom {
    //Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), find the minimum number of conference rooms required.
    // Input: intervals = [[0,30],[5,10],[15,20]]
    // Output: 2
    // Input: intervals = [[7,10],[2,4]]
    // Output: 1

    public int minMeetingRoom(int[][] intervals) {
        //sort by start time
        Arrays.sort(intervals, (a,b) -> a[0] - b[0]);

        //earliest meeting is 0,30
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int[] meeting: intervals) {
            int start = meeting[0];
            int end = meeting[1];

            if (!queue.isEmpty() && queue.peek() <= start) {
                queue.poll();
            }
            
            queue.add(end);
        }
        
        return queue.size();
    }
}
