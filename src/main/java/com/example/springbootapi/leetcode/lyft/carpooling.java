package com.example.springbootapi.leetcode.lyft;

import java.util.Arrays;
import java.util.TreeMap;

public class carpooling {

    // There is a car with capacity empty seats. The vehicle only drives east (i.e., it cannot turn around and drive west).
    // You are given the integer capacity and an array trips where trips[i] = [numPassengersi, fromi, toi] indicates that the ith trip has numPassengersi passengers and the locations to pick them up and drop them off are fromi and toi respectively. 
    // The locations are given as the number of kilometers due east from the car's initial location.
    
    // Return true if it is possible to pick up and drop off all passengers for all the given trips, or false otherwise.   

    // Example 1:

    // Input: trips = [[2,1,5],[3,3,7]], capacity = 4
    // Output: false
    // Example 2:

    // Input: trips = [[2,1,5],[3,3,7]], capacity = 5
    // Output: true
 
    //example 3:
    //trips = [[3,2,8],[2,1,5],[4,6,9]], capacity = 6
    //output is what?
    // lets sort on the fromi time [2,1,5], [3,2,8], [4,6,9] capacity is 6. after first you get on, passenger is 5 now. next on gets on at 6, at 5km 2 leave so 3 passengers + 4 this retuyrns false

    public boolean carPooling(int[][] trips, int capacity) {
        //already sorsts
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        for (int[] trip: trips) {
            treeMap.put(trip[1], treeMap.getOrDefault(trip[1], 0) + trip[0]); //track the TO km
            treeMap.put(trip[2], treeMap.getOrDefault(trip[2], 0) - trip[0]); //track the FROM km, minus the passenger
        }

        int current = 0;
        for (int passengers: treeMap.values()) {
            current += passengers;

            if (current > capacity) {
                return false;
            }
        }

        return true;
    }
}
