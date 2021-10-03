package com.example.corejava.socket.l1;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SocketTest {

    public static void main(String[] args) throws UnknownHostException, IOException {
        try (Socket s = new Socket("time-a.nist.gov", 13);
                Scanner in = new Scanner(s.getInputStream(), StandardCharsets.UTF_8.name())) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);
            }
        }
    }

}
