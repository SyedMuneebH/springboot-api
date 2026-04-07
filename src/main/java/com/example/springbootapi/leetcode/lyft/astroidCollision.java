package com.example.springbootapi.leetcode.lyft;

import java.util.Stack;

public class astroidCollision {
    // We are given an array asteroids of integers representing asteroids in a row. The indices of the asteroid in the array represent their relative position in space.
    // For each asteroid, the absolute value represents its size, and the sign represents its direction (positive meaning right, negative meaning left). Each asteroid moves at the same speed.
    // Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode. If both are the same size, both will explode. Two asteroids moving in the same direction will never meet.


    // Example 1:
    // Input: asteroids = [5,10,-5] ---- 5,3,-5

    //[5 --->, 10 ---> , <---- -5]
    //-5 and 10 are attacking each other. 10 > 5 +- just direction. 
    //new list is [5, 10] .. both same direction then we are good
    // Output: [5,10]
    // Explanation: The 10 and -5 collide resulting in 10. The 5 and 10 never collide.
    // Example 2:

    // Input: asteroids = [8,-8]
    // [8 ---> ,<---- -8]
    // 8 > 8, 8==8, 8 < 8 this 
    // Output: []
    // Explanation: The 8 and -8 collide exploding each other.

    //stack over queue here, stack is LIFO we need LIFO here [5,10,-1,5]
    //stack is [5,10] now -1 comes it needs to face 10 not 5, 10 is LIFO


    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        for (int asteroid: asteroids) {
            boolean incomingAstroidDestroyed = false;
            while (!stack.isEmpty() && asteroid < 0 && stack.peek() > 0) { //if stack is full, asteroid is neg so moving left, peeking at lifo stack adn its positive, collision will happen
                if (stack.peek() > asteroid * -1) {
                    incomingAstroidDestroyed = true;
                    break; //nothing happens stack stays the same...
                } else if (stack.peek() < asteroid * -1) {
                    stack.pop();
                } else {
                    incomingAstroidDestroyed = true;
                    stack.pop();
                    break;
                }
            }
            if (!incomingAstroidDestroyed) {
                stack.push(asteroid);
            }
        }

        int[] result = new int[stack.size()]; //your returning an int[]

        for (int i = stack.size() -1; i >=0; i--) {
            result[i] = stack.pop();
        }
        return result;
    }
}
