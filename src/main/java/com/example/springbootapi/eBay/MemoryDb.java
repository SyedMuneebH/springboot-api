package com.example.springbootapi.eBay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryDb {
    /*
    
    Level 1: Core CRUD Operations 
    The goal is to implement a basic key-value store where records contain multiple fields. 
    Method Signatures: Usually SET(key, field, value), GET(key, field), and DELETE(key, field).
    Key Requirements:
    SET should create a record if the key doesn't exist and add/update the specific field.
    DELETE should return a boolean indicating if the field was successfully removed.
    
    Level 2: Data Processing & Filtering
    This level tests your ability to query and sort the stored data. 
    CodeSignal
    CodeSignal
    New Methods: Often includes SCAN(key) to return all fields in a record, or SCAN_PREFIX(key, prefix) to return fields starting with a specific string.
    Sorting Logic: Results must typically be returned in a specific order, such as alphabetically by field name.

    Level 3: Advanced Features (TTL - Time-To-Live) 
    This level introduces the concept of expiration, requiring you to track when data was added. 
    GitHub
    GitHub
    +1
    Updated Signatures: Methods are updated mp, such as SETto include a timesta_AT(key, field, value, timestamp, ttl).
    Core Logic:
    A field is only "active" during the interval [timestamp, timestamp + ttl].
    Any GET or SCAN call at a later timestamp must treat expired fields as if they don't exist.

    Level 4: Design Extension (Versioning / Look-back) 
    The final level requires retrieving data as it existed at a specific point in time. 
    Reddit
    Reddit
    +1
    New Method: GET_AT_VERSION(key, field, timestamp) or BACKUP(timestamp).
    Core Logic: You must maintain a history of all changes. If a value was "A" at time 10 and "B" at time 20, a "look-back" request for time 15 must return "A".
    Industry Expectation: This assesses your ability to design complex data structures (like a TreeMap or a List of versioned objects) that allow for efficient searching of historical states.
    */

    HashMap<String, HashMap<String, String>> keyValueMap; // (key, <key,value>)
    public MemoryDb() {
        keyValueMap = new HashMap<>();
    }

    public void set(String key, String field, String value, int timestamp, int ttl) {
        if (!keyValueMap.containsKey(key)) {
            keyValueMap.put(key, new HashMap<>());
        }

        int expiry = timestamp + ttl;

        keyValueMap.get(key).put(field, new FieldData(value, expiry));


    }

    public String get(String key, String field, int currentTimestamp) {
        if (!keyValueMap.containsKey(key)) {
            return "";
        }

        HashMap<String, String> map = keyValueMap.get(key);
        if (!map.containsKey(field)) {
            return "";
        }

        return map.get(field);
    }

    public boolean delete(String key, String field) {
        if (!keyValueMap.containsKey(key)) {
            return false;
        }
        HashMap<String, String> map = keyValueMap.get(key);
        if (!map.containsKey(field)) {
            return false;
        }
        map.remove(field);
        if (keyValueMap.isEmpty()) {
            keyValueMap.remove(key);
        }

        return true;
    }

    public List<String> Scan(String key) {
        //return all records in key sorted.
        // SET("user1", "zip", "90210")
        // SET("user1", "age", "25")
        // SCAN("user1") -> should return ["age(25)", "zip(90210)"]

        if (!keyValueMap.containsKey(key)) {
            return new ArrayList<>();
        }

        HashMap<String, String> map = keyValueMap.get(key);
        List<String> fieldNames = new ArrayList<>(map.keySet());
        List<String> result = new ArrayList<>();
        
        fieldNames.sort((a,b) -> {
            return a.compareTo(b);
        });

        for(Map.Entry<String, String> entry : map.entrySet()) {
            String innerKey = entry.getKey();
            String innerValue = entry.getValue();
            result.add(innerKey + "(" + innerValue + ")");
        }

        return result;
    }


}


    private class FieldData {
        String value;
        Long expiryTime;

        public FieldData(String v, Long t) {
            this.value = v;
            this.expiryTime = t;
        }

    }
