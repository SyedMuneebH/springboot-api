package com.example.springbootapi.leetcode.lyft;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class waterbucket {
    public boolean canMeasureWater(int jug1, int jug2, int target) {
        // Physical impossibility check
        if (jug1 + jug2 < target) return false;
        
        // This Queue will hold the "total water" we can currently have
        Queue<Integer> queue = new LinkedList<>();
        // This Set keeps track of totals we've already tried (to avoid infinite loops)
        Set<Integer> visited = new HashSet<>();
        
        // Start with 0 water
        queue.offer(0);
        visited.add(0);
        
        while (!queue.isEmpty()) {
            int currentTotal = queue.poll();
            
            // Did we hit the target?
            if (currentTotal == target) {
                return true;
            }
            
            // What are our possible "moves" from the current total?
            // We can add jug1, subtract jug1, add jug2, or subtract jug2.
            int[] moves = {jug1, -jug1, jug2, -jug2};
            
            for (int move : moves) {
                int nextTotal = currentTotal + move;
                
                // Stay within valid boundaries (0 to max capacity)
                if (nextTotal >= 0 && nextTotal <= jug1 + jug2 && !visited.contains(nextTotal)) {
                    queue.offer(nextTotal);
                    visited.add(nextTotal);
                }
            }
        }
        
        return false;
    }
    
}
