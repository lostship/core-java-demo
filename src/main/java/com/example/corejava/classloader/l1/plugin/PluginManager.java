package com.example.corejava.classloader.l1.plugin;

public class PluginManager {

    private static IPlugin plugin;

    public static synchronized void registerPlugin(com.example.corejava.classloader.l1.plugin.IPlugin impl) {
        plugin = impl;
        System.out.println("registered");
    }

    public static void runSuccess() {
        plugin.runSuccess();
    }

    public static void runFail() {
        plugin.runFail();
    }

    public static Class<?> loadClass(String className) throws ClassNotFoundException {
        System.out.println("actually execute class loader: " + PluginManager.class.getClassLoader());
        return Class.forName(className);
    }

    public static Class<?> loadClass(String className, ClassLoader loader) throws ClassNotFoundException {
        return Class.forName(className, false, loader);
    }

}
