package com.example.springbootapi.openloop;

public interface inMemoryInterface {

    boolean addFile(String name, int size);

    boolean copyFile(String nameFrom, String nameTo);

    Integer getFileSize(String name);
}
