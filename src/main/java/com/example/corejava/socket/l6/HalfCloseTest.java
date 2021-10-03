package com.example.corejava.socket.l6;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 半关闭（half-close）。向服务器传输数据，但是一开始并不知道要传输多少数据。<br>
 * 在向文件写数据时，只需在数据写入后关闭文件即可。但是如果关闭一个socket，那么与服务器的连接断开，就无法读取服务器的相应了。<br>
 * 半关闭可以解决上述问题。可以关闭一个socket的输出流表示发送给服务器的请求数据已经结束，但是保持输入流的打开状态。<br>
 * 服务器端将读取输入信息，直至到达输入流的结尾，然后它再发送响应。<br>
 * 该协议适用于一站式（one-shot）的服务，客户端连接服务器，发送一个请求，获得响应，然后断开连接。<br>
 */
public class HalfCloseTest {

    public static void main(String[] args) {
        try (Socket s = new Socket("localhost", 8191)) {
            Scanner in = new Scanner(s.getInputStream(), "UTF-8");
            PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));

            // send request data
            out.print("");
            out.flush();
            s.shutdownOutput();
            // now socket is half-closed
            // read response data
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
