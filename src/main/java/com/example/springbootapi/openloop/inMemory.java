package com.example.springbootapi.openloop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class inMemory implements inMemoryInterface {
    /*
    The Scenario:
    Your task is to implement a basic in-memory cloud storage system. You need to provide an interface 
    that allows users to add files, check their sizes, and copy existing files. All data should be stored 
    in memory for the duration of the execution.

    ADD_FILE(name: string, size: integer) -> boolean
    Description: Creates a new file with the given name and size.
    Returns: true if the file was created successfully. If a file with the same name
    already exists, it should not be overwritten; instead, return false.
    
    COPY_FILE(name_from: string, name_to: string) -> boolean
    Description: Creates a copy of an existing file.
    Returns: true if the file name_from exists and name_to does not yet exist.
    In all other cases (e.g., name_from is missing or name_to already exists), return false.
    
    GET_FILE_SIZE(name: string) -> integer
    Description: Retrieves the size of the specified file.
    Returns: The size of the file if it exists. If the file does not exist,
    return null
    */

    private HashMap<String, File> fileStorage;
    private HashMap<String,User> users;
    public inMemory() {
        this.fileStorage = new HashMap<>();
        this.users = new HashMap<>();
    }

    public boolean addFile(String name, int size) {
        if (fileStorage.containsKey(name)) {
            return false;
        }

        fileStorage.put(name, new File(name, size));
        return true;
    }

    public boolean copyFile(String nameFrom, String nameTo) {
        if (nameFrom.isEmpty() || nameTo.isEmpty()) {
            return false;
        }
        if (fileStorage.containsKey(nameTo) || !fileStorage.containsKey(nameFrom)) {
            return false;
        }

        File file = fileStorage.get(nameFrom);

        fileStorage.put(nameTo, file);

        return true;
    }

    public Integer getFileSize(String name) {
        if (!fileStorage.containsKey(name)) {
            return null;
        }
        File file = fileStorage.get(name);

        return file.size;
    }

    public boolean deleteFile(String name) {
        if (!fileStorage.containsKey(name)) {
            return false;
        }
        File file = fileStorage.get(name);
        User user = users.getOrDefault(file.userId, null);
        if (user != null) {
            user.currentUsage = user.currentUsage - file.size;
        }
        
        fileStorage.remove(name);

        return true;
    }

    public List<String> findFilesByPrefix(String pre) {

        List<File> unsorted = new ArrayList<>();
        if (fileStorage.size() < 1) {
            return new ArrayList<>();
        }

        for (Map.Entry<String, File> entry: fileStorage.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith(pre)) { //ai helped with this i had contains
                unsorted.add(entry.getValue());
            }
        }
        // needed to get this done with ai
        unsorted.sort((a,b) -> {
            if (!a.size.equals(b.size)) {
                return b.size - a.size;
            } else {
                return a.name.compareTo(b.name);
            }
        });

        List<String> result = new ArrayList<>();
        for(File f: unsorted) {
            result.add(f.name);
        }

        return result;
    }

    public boolean addUser(String userId, int capacity) {
        if (users.containsKey(userId)) {
            return false;
        }
        users.put(userId, new User(0, capacity));
        return true;
    }

    public boolean addFileByUser(String userId, String name, int size) {
        if (!users.containsKey(userId)) {
            return false;
        }

        if (fileStorage.containsKey(name)) {
            return false;
        }
        if (users.get(userId).capacity < size || users.get(userId).currentUsage + size > users.get(userId).capacity) {
            return false;
        }

        File file = new File(name, size);
        file.setUser(userId);
        User user = users.get(userId);
        user.currentUsage += size;
        fileStorage.put(name, file);

        return true;
    }

    public Integer updateCapacity(String userId, Integer newCapacity) {
        int count = 0;
        if (!users.containsKey(userId)) {
            return null;
        }
        for (Map.Entry<String, File> entry : fileStorage.entrySet()) {
            if (entry.getValue().userId.equals(userId)) {
                count +=1;
            }
        }
        users.get(userId).capacity = newCapacity;
        return count;
    }

    private class File {
        String name;
        Integer size;
        String userId = "";

        public File(String name, int size) {
            this.name = name;
            this.size = size;
        } 

        public void setUser(String userId) {
            this.userId = userId;
        }

        public String getUser() {
            return this.userId;
        }
    }

    private class User{
        Integer currentUsage;
        Integer capacity;
        
        public User(Integer usage, Integer cap) {
            this.currentUsage = usage;
            this.capacity = cap;
        }
    }
}
