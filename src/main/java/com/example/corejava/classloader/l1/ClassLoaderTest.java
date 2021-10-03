package com.example.corejava.classloader.l1;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.example.corejava.classloader.l1.plugin.PluginManager;

public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        ClassLoader pcl = Thread.currentThread().getContextClassLoader();
        System.out.println(pcl);
        while (pcl != null) {
            pcl = pcl.getParent();
            System.out.println(pcl);
        }
        System.out.println();

        System.out.println(String.class.getClassLoader());
        System.out.println(java.sql.Driver.class.getClassLoader());
        System.out.println(java.sql.Driver.class.getClassLoader().getParent());
        System.out.println(PluginManager.class.getClassLoader());
        System.out.println(PluginManager.class.getClassLoader().getParent());
        System.out.println(PluginManager.class.getClassLoader().getParent().getParent());
        System.out.println();

        Path path = Paths.get("d:/Plugin.jar");
        JarFile jarFile = new JarFile(path.toFile());
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            System.out.println(entry.getName());
        }
        System.out.println();

        URL pluginURL = path.toUri().toURL();

        URLClassLoader loader = new URLClassLoader(new URL[] { pluginURL });
        Thread.currentThread().setContextClassLoader(loader);

        // 这样写是不会执行static初始化块的，必须要initialize = true才会执行static初始化块
        // Class<?> cl = loader.loadClass("com.example.corejava.classloader.l1.plugin.impl.PluginImpl");
        // Class<?> cl = Class.forName("com.example.corejava.classloader.l1.plugin.impl.PluginImpl", false, loader);

        Class<?> cl = Class.forName("com.example.corejava.classloader.l1.plugin.impl.PluginImpl", true, loader);
        System.out.println("loaded plugin class: " + cl);
        System.out.println();

        PluginManager.runFail();
    }

}
