package com.example.springbootapi.leetcode.lyft;

import java.util.LinkedList;
import java.util.Queue;

import org.hibernate.sql.ast.tree.expression.Every;

public class rottenOranges {
//dfs problem
    //994. Rotting Oranges
    // You are given an m x n grid where each cell can have one of three values:

    // 0 representing an empty cell,
    // 1 representing a fresh orange, or
    // 2 representing a rotten orange.
    // Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.

    // Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.

    
    // Input: grid = [[2,1,1],[1,1,0],[0,1,1]]  xxx
                                            //  xxx
                                            //  xxx


                                            //  xxx first x, can check up down left right
                                            //  xxx
                                            //  xxx
    // first row has 1 rotten 2 fresh
    // second row has 2 fresh 0 rotten
    // third row has 0 rotten, 2 fresh

    //what happens
    //[2,1,1] - > 1 min later -> rotten effects right on first row, 2nd row first element, after 1 min it looks like this: [2,2,1], [2,1,0] --> 2 mins in looks like this: ---  [2,2,2] [2,2,0] etc

    // Output: 4

    public int orangeRotting(int[][] grid) {
        //set up has queue (FIFO), mins, fresh, direction
        Queue<int[]> queue = new LinkedList<>();
        int row = grid.length;
        int col = grid[0].length;
        int fresh = 0;
        int minutes = 0;
        int[][] direction = new int[][]{{1,0}, {0,1}, {-1,0}, {0,-1}};

        ///find all rotten in initial scan throw in a queue, we need this so we can lifo it out and check every rotten orange
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j ++) {
                if (grid[i][j] == 2) {
                    queue.add(new int[]{i,j});
                }
                if (grid[i][j] == 1) {
                    fresh++;
                }
            }
        }

        while (!queue.isEmpty() && fresh > 0) {
            int size = queue.size();
            for (int i = 0; i < size; i++) { //just need a counter to see the current elements when we poll out the rotten orange
                int[] current = queue.poll();
                ///loop thru all directions, get new coordinates
                for (int[] dir: direction) {
                    int newRow = dir[0] + current[0];
                    int newCol = dir[1] + current[1];
                    //check new coridnates is inside the index of our 3x3, check if it is a rotten orrange then change it to rotten, decrement fresh, add to queue.
                    if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length && grid[newRow][newCol] == 1) {
                        grid[newRow][newCol] = 2; //fresh turns rotten now,
                        queue.add(new int[]{newRow,newCol});
                        fresh--;
                    }
                }
            }
            minutes++;
        }

        if (fresh > 0) {
            return -1; //since fresh still exists....
        } else {
            return minutes;
        }
    }
}
