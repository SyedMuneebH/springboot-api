package com.example.springbootapi.eBay;
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
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class memory {
    HashMap<String, HashMap<String,FieldValue>> map;
    
    public memory() {
        this.map = new HashMap<>();
    }

    public void set(String key, String field, String value) {
        if (!map.containsKey(key)) {
            map.put(key, new HashMap<>());
        }

        map.get(key).put(field, new FieldValue(value));
    }

    public String get(String key, String field) {
        if (!map.containsKey(key)) {
            return null;
        }

        HashMap<String, FieldValue> fields = map.get(key);
        if (!fields.containsKey(field)) {
            return null;
        }

        return fields.get(field).value;
    }

    public boolean del(String key, String field) {
        if (!map.containsKey(key)) {
            return false;
        } 

        HashMap<String, FieldValue> fields = map.get(key);

        if (!fields.containsKey(field)) {
            return false;
        }

        fields.remove(field);

        return true;
    }

    public String scan(String key) {
        if (!map.containsKey(key)) {
            return "";
        }
        HashMap<String, FieldValue> fields = map.get(key);
        List<String> unsorted = new ArrayList<>();

        for (Map.Entry<String, FieldValue> entry: fields.entrySet()) {
            unsorted.add(entry.getKey());
        }

        unsorted.sort((a, b) -> a.compareTo(b));
        return String.join(", ", unsorted);

        /*
        key1, <field1, value1>, <field2, value2> etc... 
        map - > map <string, and fvs>
        1 key can have multiple records in the inner map.

        */
    }

    private class FieldValue {
        String value;

        public FieldValue(String value) {
            this.value = value;
        }
    }
}
