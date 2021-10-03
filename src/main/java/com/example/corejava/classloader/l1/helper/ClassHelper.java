package com.example.corejava.classloader.l1.helper;

public class ClassHelper implements IClassHelper {

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

}
