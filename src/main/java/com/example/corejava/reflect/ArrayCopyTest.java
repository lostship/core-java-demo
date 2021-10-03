package com.example.corejava.reflect;

import java.lang.reflect.Array;

public class ArrayCopyTest {

    // 原始数组类型可以转换成Object，但不能转换成包装类数组，因此参数应该声明为Object类型，而不是Object[]
    public static <T> T copyOf(Object a, int newLength) {
        Class cl = a.getClass();
        if (!cl.isArray()) {
            return null;
        }

        Class componentType = cl.getComponentType();
        int length = Array.getLength(a);
        Object newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(a, 0, newArray, 0, Math.min(length, newLength));
        return (T) newArray;
    }

}
