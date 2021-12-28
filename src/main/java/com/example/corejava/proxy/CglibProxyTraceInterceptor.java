package com.example.corejava.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxyTraceInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // if ("toString".equals(method.getName())) {
        // return obj.getClass().getSuperclass().getMethod("toString").invoke(obj);
        // }

        System.out.print(obj + "." + method.getName() + "(");
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print(args[i]);
            }
        }
        System.out.println(")");

        // invoke actual method
        return proxy.invokeSuper(obj, args);
    }

}
