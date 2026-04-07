package com.example.springbootapi.leetcode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class queues {
    

    public void passengers() {
        Queue<String> queue = new LinkedList<>();
        queue.offer("Alice");
        queue.offer("Bob");

        while(!queue.isEmpty()) {
            String passenger = queue.poll();
            System.out.println(passenger);
        }
    }

    //priorityqueue

    public void priority() {
        Queue<Integer> line = new PriorityQueue<>();
        line.offer(500);
        line.offer(300);
        line.offer(200);
        //to change it to return MAX instead of default min we need to add comparator 
        Queue<Integer> maxHeap = new PriorityQueue<>((a,b) -> b-a); //descending 12,11,10,9,7 etc
        maxHeap.offer(1);
        maxHeap.offer(12);
        maxHeap.offer(124);                
        Queue<Integer> minHeap = new PriorityQueue<>((a,b) -> a-b); //ascending 0,1,2,3,4 etc
        minHeap.offer(1);
        minHeap.offer(12);
        minHeap.offer(124);    


        while (!minHeap.isEmpty()) {
            Integer price = minHeap.poll();
            System.out.println(price);
            //since we use priority queue, it will go lowest to highest, 200,300,500
        }

        while (!maxHeap.isEmpty()) {
            Integer price = maxHeap.poll();
            System.out.println(price);
            //since we use priority queue, it will go lowest to highest, 200,300,500
        }
    }

    /*
    lets do a big problem.  dfs/bfs/string shit/everything.
    1. The "Top K Cheapest Flights"
    Input: A list of raw strings: ["YYZ-LHR-500", "JFK-LAX-200", "LHR-CDG-300", "YYZ-JFK-100"]
    Goal: Return the top 2 cheapest flight routes as a list of Objects.
    Hint: Parse the string, create a Flight class, and use a PriorityQueue with a custom comparator. yyz, [lhr, price]
    answer will be yyz-jfk-100 and jfk-lax-200 --- parse by - store in obj i make, Comparator.comparing(flight::getPrice), ((a,b) -> a[0] - b[0])
    */

    public List<Flight> cheapestFlights(String[] flightPaths, int k) {
        List<Flight> results = new ArrayList<>();
        Queue<Flight> flights = new PriorityQueue<>(Comparator.comparingInt(Flight::price)); // this will sort the flight object by cheapest.
        
        for(String booking: flightPaths) {
            String[] parts = booking.split("-");
            flights.offer(new Flight(parts[0], parts[1], Integer.parseInt(parts[2]))); //convert String price to an integer.
        }
        //now we have a priority queue that is sorted by prices
        
        while (!flights.isEmpty() && results.size() < k) {
            results.add(flights.poll());
        }


        return results;

        //improvements we can try catch this, we can add null checkers and integer . parseInt make sure there no leading spaces etc.
    }
    
    public record Flight(String src, String dest, Integer price) {} //record handles getters , setters, instantiating

        public static void main(String[] args) {
        queues solution = new queues();
        solution.priority();
    }


    /*
    There are n cities connected by some number of flights. You are given an array flights where flights[i] = [fromi, toi, pricei] indicates that there is a flight from city fromi to city toi with cost pricei.
    You are also given three integers src, dst, and k, return the cheapest price from src to dst with at most k stops. If there is no such route, return -1.
    Input: n = 4, flights = [[0,1,100],[1,2,100],[2,0,100],[1,3,600],[2,3,200]], src = 0, dst = 3, k = 1
    Output: 700 adj === 0, [flightobjs]
    */

    public int findCheapestFlight(int n, int[][]flights, int src, int dest, int k) {
        Map<Integer, List<flightObj>> adj = new HashMap<>();
        for (int[] f : flights) {
            adj.computeIfAbsent(f[0], key -> new ArrayList<>())
            .add(new flightObj(f[0], f[1], f[2]));
}
        Queue<flightObj> queue = new PriorityQueue<>(Comparator.comparingInt(flightObj::price));
        for (int i = 0; i < flights.length; i++) {
            int[] current = flights[i];
            queue.offer(new flightObj(current[0], current[1], current[2]));
        }

        //now we have queue that is sorted by price
        while (!queue.isEmpty()) {
            flightObj result = queue.poll();
        }
        return -1;
    }

    public record flightObj(int src, int dest, int price) {}




}
