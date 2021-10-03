package com.example.corejava.classloader.l1.plugin.impl;

/**
 * 类加载器翻转（class loader inversion）
 *
 * 将plugin.impl中的模拟插件编译并打成jar包，放到程序classpath之外：
 * javac com/example/corejava/classloader/l1/plugin/impl/PluginImpl.java
 * javac com/example/corejava/classloader/l1/plugin/impl/PluginWorker.java
 * jar cvf D:/Plugin.jar com/example/corejava/classloader/l1/plugin/impl/*.class
 * 
 * 自定义class loader加载PluginImpl类，之后调用runXXX方法：
 * - runFail，不指定class loader，PluginManager中调用Class.forName就使用加载PluginManager类的类加载器，
 * 本例中为默认类加载器system class loader，对它来说插件中的类不可见（，Plugin.jar不在classpath类路径下），就无法加载PluginWorker类。
 * - runSuccess，传递context class loader（已经被设置成plugin class loader），
 * PluginManager中调用Class.forName时使用该class loader就可以加载PluginWorker类。
 */
public class PluginImpl implements com.example.corejava.classloader.l1.plugin.IPlugin {

    static {
        try {
            System.out.println("register plugin impl");
            com.example.corejava.classloader.l1.plugin.PluginManager.registerPlugin(new PluginImpl());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void runSuccess() {
        System.out.println("plugin impl context class loader: " + Thread.currentThread().getContextClassLoader());

        try {
            Class<?> cl = com.example.corejava.classloader.l1.plugin.PluginManager.loadClass(
                    "com.example.corejava.classloader.l1.plugin.impl.PluginWorker",
                    Thread.currentThread().getContextClassLoader());
            System.out.println("loaded class: " + cl);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void runFail() {
        System.out.println("plugin impl context class loader: " + Thread.currentThread().getContextClassLoader());

        try {
            Class<?> cl = com.example.corejava.classloader.l1.plugin.PluginManager.loadClass(
                    "com.example.corejava.classloader.l1.plugin.impl.PluginWorker");
            System.out.println("loaded class: " + cl);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
