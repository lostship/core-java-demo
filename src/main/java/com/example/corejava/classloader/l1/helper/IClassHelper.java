package com.example.corejava.classloader.l1.helper;

public interface IClassHelper {

    Class<?> loadClass(String className) throws ClassNotFoundException;

}
