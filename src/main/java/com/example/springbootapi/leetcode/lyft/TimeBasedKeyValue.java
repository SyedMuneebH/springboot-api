package com.example.springbootapi.leetcode.lyft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimeBasedKeyValue {
    //HASHMAP PROBLEM, BINARY SEARCH PROBLEM
    // Design a time-based key-value data structure that can store multiple values for the same key at different time stamps and retrieve the key's value at a certain timestamp.

    // Implement the TimeMap class:

    // TimeMap() Initializes the object of the data structure.
    // void set(String key, String value, int timestamp) Stores the key key with the value value at the given time timestamp.
    // String get(String key, int timestamp) Returns a value such that set was called previously, with timestamp_prev <= timestamp. If there are multiple such values, it returns the value associated with the largest timestamp_prev. If there are no values, it returns "".



    //     Input
    // ["TimeMap", "set", "get", "get", "set", "get", "get"]
    // [[], ["foo", "bar", 1], ["foo", 1], ["foo", 3], ["foo", "bar2", 4], ["foo", 4], ["foo", 5]]
    // Output
    // [null, null, "bar", "bar", null, "bar2", "bar2"]

    // Explanation
    // TimeMap timeMap = new TimeMap();
    // timeMap.set("foo", "bar", 1);  // store the key "foo" and value "bar" along with timestamp = 1.
    // timeMap.get("foo", 1);         // return "bar"
    // timeMap.get("foo", 3);         // return "bar", since there is no value corresponding to foo at timestamp 3 and timestamp 2, then the only value is at timestamp 1 is "bar".
    // timeMap.set("foo", "bar2", 4); // store the key "foo" and value "bar2" along with timestamp = 4.
    // timeMap.get("foo", 4);         // return "bar2"
    // timeMap.get("foo", 5);         // return "bar2"

     HashMap<String, List<PairValues>> map = new HashMap<>();

    public HashMap<String, List<PairValues>> TimeMap() {
        map = new HashMap<>();
        return map;
        //map  is basically <string, <list<value,timestamp>>
    }

    public void set(String key, String value, Integer timestamp) {
        ArrayList<PairValues> pairValue = new ArrayList<>();
        pairValue.add(new PairValues(value, timestamp));

        if (!map.containsKey(key)) {
            map.put(key, pairValue);
        } else {
            map.get(key).add(pairValue.get(0));
        }
    }

    // public String get(String key, Integer timeStamp) {
    //     if (!map.containsKey(key)) {
    //         return "";
    //     }
    //     int prevTimeStamp = -1; //need a prev time stamp
    //     String result = ""; // result that we return 

    //     for (PairValues pair: map.get(key)) { //loop thru the valueset
    //         if (pair.getTimeStamp() <= timeStamp) { //check timestamp and make sur every timestamp we check is less than the timestamp that we have as param
    //             // if (pair.getTimeStamp() >= prevTimeStamp) { // now if value timeset is larger than our prev, store the result. and reset the prev to current. this way now largest value associated w timestamp is printed.
    //             //     result = pair.getValue();
    //             //     prevTimeStamp = pair.getTimeStamp();
    //             // }
    //             result = pair.getValue(); //dont need inner if statement since arraylist is sorted already.
    //         } else {
    //             break;
    //         }
    //     }

    //     return result;
    // }

    //binary search makes sense now
    public String get(String key, Integer timestamp) {
        if (!map.containsKey(key)) {
            return "";
        }
        String result = "";
        List<PairValues> pairValues = map.get(key);
        int leftSearch = 0;
        int rightSearch = pairValues.size() - 1;

        while (leftSearch <= rightSearch) {
            int mid = (leftSearch + rightSearch) / 2;
            if (pairValues.get(mid).getTimeStamp() <= timestamp) {
                result = pairValues.get(mid).getValue();
                // continue searching for a bigger number on the right
                leftSearch = mid + 1;
            } else {
                rightSearch = mid - 1;
            }
        }

        return result;
    }
}

//search just hashmap
    // public String get(String key, Integer timeStamp) {
    //     if (!map.containsKey(key)) {
    //         return "";
    //     }
    //     int prevTimeStamp = -1; //need a prev time stamp
    //     String result = ""; // result that we return 

    //     for (PairValues pair: map.get(key)) { //loop thru the valueset
    //         if (pair.getTimeStamp() <= timeStamp) { //check timestamp and make sur every timestamp we check is less than the timestamp that we have as param
    //             if (pair.getTimeStamp() >= prevTimeStamp) { // now if value timeset is larger than our prev, store the result. and reset the prev to current. this way now largest value associated w timestamp is printed.
    //                 result = pair.getValue();
    //                 prevTimeStamp = pair.getTimeStamp();
    //             }
    //         }
    //     }

    //     return result;
    // }

    class PairValues {
        String value;
        Integer timeStamp;

        public PairValues(String value, Integer timeStamp) {
            this.value = value;
            this.timeStamp = timeStamp;
        }

        public String getValue() {
            return value;
        }

        public Integer getTimeStamp() {
            return timeStamp;
        }
    }
