package com.example.corejava.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Orientation implements Serializable {

    public static final Orientation HORIZONTAL = new Orientation(1);
    public static final Orientation VERTICAL = new Orientation(2);

    private int value;

    public Orientation() {
        System.out.println("constructor");
    }

    private Orientation(int value) {
        this.value = value;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        System.out.println("write object");
    }

    private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        System.out.println("read object");
    }

    protected Object readResolve() throws InvalidObjectException {
        System.out.println("read resolve");
        System.out.println("read " + this);
        if (value == 1)
            return HORIZONTAL;
        if (value == 2)
            return VERTICAL;
        throw new InvalidObjectException(null);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Orientation original = Orientation.HORIZONTAL;
        System.out.println("original " + original);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream os = new ObjectOutputStream(bos)) {
            os.writeObject(original);
        }

        Orientation saved;
        try (ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()))) {
            saved = (Orientation) is.readObject();
        }

        System.out.println("saved " + saved);
        System.out.println(saved.value);
        System.out.println(saved == original);
    }

}
