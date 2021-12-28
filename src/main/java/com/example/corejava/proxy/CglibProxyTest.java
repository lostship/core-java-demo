package com.example.corejava.proxy;

import java.lang.reflect.Method;

import com.example.corejava.classes.class1.Manager;

import net.sf.cglib.proxy.CallbackHelper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class CglibProxyTest {

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        Class<?> cl = Manager.class;
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(cl.getClassLoader());
        enhancer.setSuperclass(cl);
        CallbackHelper callbackHelper = new CallbackHelper(cl, null) {
            @Override
            protected Object getCallback(Method method) {
                if ("toString".equals(method.getName())
                        || "getName".equals(method.getName())) {
                    return NoOp.INSTANCE;
                }
                return new CglibProxyTraceInterceptor();
            }
        };
        enhancer.setCallbackFilter(callbackHelper);
        enhancer.setCallbacks(callbackHelper.getCallbacks());
        // enhancer.setCallback(new CglibProxyTraceInterceptor());
        Manager manager = (Manager) enhancer.create(
                new Class[] { String.class, double.class, double.class },
                new Object[] { "Tom", 1000, 500 });
        manager.hello("Jerry");
    }

}
