package com.example.corejava.socket.l5;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 这个程序为每个链接生成一个单独的线程。这种方法并不能满足高性能服务器的要求。为使服务器实现更高的吞吐量，可以使用java.nio包中的一些特性。
 */
public class ServiceTest {

    public static void main(String[] args) {
        try (ServerSocket s = new ServerSocket(8189)) {
            int i = 1;

            while (true) {
                Socket incoming = s.accept();
                System.out.println("Spawning " + i);
                Runnable r = new ThreadedEchoHandler(incoming);
                Thread t = new Thread(r);
                t.start();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class ThreadedEchoHandler implements Runnable {

    private final Socket incoming;

    public ThreadedEchoHandler(Socket incoming) {
        this.incoming = incoming;
    }

    @Override
    public void run() {
        try (InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream()) {
            Scanner in = new Scanner(inStream, "UTF-8");
            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(outStream, "UTF-8"),
                    true /* autoFlush */);

            out.println("Hello! Enter BYE to exit.");

            // echo client input
            boolean done = false;
            while (!done && in.hasNextLine()) {
                String line = in.nextLine();
                out.println("Echo: " + line);
                if (line.trim().equalsIgnoreCase("BYE")) {
                    done = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
