package com.example.corejava.io.l2;

import java.nio.IntBuffer;

public class BufferTest {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        buffer.put(1);
        buffer.put(2);
        buffer.put(3);
        buffer.put(4);
        buffer.flip();

        System.out.println(buffer.position());
        for (int i = 0; i < 3; i++) {
            buffer.get();
        }

        System.out.println(buffer.position());
        buffer.put(5);
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.remaining());
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }

}
