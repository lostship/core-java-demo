package com.example.corejava.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 继承该类就可以实现通过序列化进行对象clone。但是要注意这种方式比显示地构建新对象并复制或克隆数据域的克隆方法慢得多。
 */
@SuppressWarnings("serial")
public abstract class SerialCloneable implements Cloneable, Serializable {

    @Override
    protected Object clone() throws CloneNotSupportedException {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try (ObjectOutputStream out = new ObjectOutputStream(bout)) {
                out.writeObject(this);
            }

            try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bout.toByteArray()))) {
                return in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            CloneNotSupportedException e2 = new CloneNotSupportedException();
            e2.initCause(e);
            throw e2;
        }
    }

}
