package com.example.corejava.socket.l5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class NioSocketTest {

    public static void main(String[] args) throws IOException {
        try (ServerSocketChannel server = ServerSocketChannel.open();
                Selector selector = Selector.open()) {
            server.bind(new InetSocketAddress(8189));
            server.configureBlocking(false);
            server.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                for (SelectionKey key : selectedKeys) {
                    if (key.isAcceptable()) {
                        SocketChannel socket = server.accept();
                        socket.configureBlocking(false);
                        socket.register(selector, SelectionKey.OP_READ);
                    }

                    if (key.isReadable()) {
                        SocketChannel socket = (SocketChannel) key.channel();

                    }
                }
            }
        }

    }

}
