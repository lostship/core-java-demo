package com.example.corejava.classloader.l3;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

public class VerifierTest {

    public static void main(String[] args) throws Exception {
        ClassLoader loader = new URLClassLoader(new URL[] { Paths.get("d:/workspace/test/tmp").toUri().toURL() });
        Class<?> cl = loader.loadClass("Malicious");
        System.out.println(cl);
        cl.getMethod("main", String[].class).invoke(null, new String[] {});
    }

}
