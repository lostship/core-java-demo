package com.example.corejava.socket.l3;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 主机名和因特网地址转换
 */
public class InetAddressTest {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getByName("time-a.nist.gov");
        printAddress(address);

        // 获取本机地址如果使用localhost，一直是回环地址
        printAddress(InetAddress.getByName("localhost"));
        printAddress(InetAddress.getLocalHost());
    }

    public static void printAddress(InetAddress address) {
        System.out.println(address);
    }

}
