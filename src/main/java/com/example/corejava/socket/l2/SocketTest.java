package com.example.corejava.socket.l2;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketTest {

    public static void main(String[] args) {
        try (Socket s = new Socket()) {
            // 设置连接超时时间
            s.connect(new InetSocketAddress("time-a.nist.gov", 13), 5000);

            // 设置读写操作最大时间，超过后即便socket仍旧有效，也会抛出SocketTimeoutException。超时时间必须在进入阻塞之前设置。
            // 为什么没抛异常？
            s.setSoTimeout(5000);

            InputStream in = s.getInputStream();
            Scanner scanner = new Scanner(in);
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
