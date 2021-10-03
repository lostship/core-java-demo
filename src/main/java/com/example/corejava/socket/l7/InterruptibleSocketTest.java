package com.example.corejava.socket.l7;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * interruptible socket（可中断的socket）
 * 
 * 当连接到一个socket时，当前线程会被阻塞直到建立连接或超时。<br>
 * 同样的，当通过socket读写数据时，当前线程也会被阻塞，无法调用interrupt来解除阻塞，直到操作成功或超时。<br>
 * 但是在交互式的应用中，应该允许用户取消（中断）连接。<br>
 * 
 * 为了能够中断socket，可以使用java.nio.SocketChannel类：SocketChannel channel = SocketChannel.open(new InetAddress(host, port));<br>
 * 通道并没有与之相关联的流，实际上它所拥有的read和write方法都是通过使用Buffer对象实现的（了解nio缓冲区知识）。<br>
 * SocketChannel实现的ReadableByteChannel、WritableByteChannel接口声明了这两个方法。<br>
 * 如果不想处理缓冲区，也可以用Scanner类从SocketChannel中读取信息，因为Scanner有ReadableByteChannel参数的构造方法。<br>
 * Scanner in = new Scanner(channel, "UTF-8");<br>
 * 通过调用静态方法Channels.newOutputStream，可以将通道转换成输出流。 OutputStream outStream = Channels.newOutputStream(channel);<br>
 */
public class InterruptibleSocketTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new InterruptibleSocketFrame();
            frame.setTitle("InterruptibleSocketTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });

        // Thread t = new Thread(() -> {
        // try (Scanner scanner = new Scanner(System.in)) {
        // while (!Thread.currentThread().isInterrupted()) {
        // if (scanner.hasNextLine()) {
        // System.out.println(scanner.nextLine());
        // }
        // }
        // }
        // });
        // t.start();
        //
        // try {
        // Thread.sleep(5000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // t.interrupt();
    }

}

class InterruptibleSocketFrame extends JFrame {

    private Scanner in;
    private JButton interruptibleButton;
    private JButton blockingButton;
    private JButton cancelButton;
    private JTextArea messages;
    private TestServer server;
    private Thread connectThread;

    public InterruptibleSocketFrame() {
        JPanel northPanel = new JPanel();
        add(northPanel, BorderLayout.NORTH);

        final int TEXT_ROWS = 20;
        final int TEXT_COLUMNS = 60;
        messages = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        add(new JScrollPane(messages));

        interruptibleButton = new JButton("Interruptible");
        blockingButton = new JButton("Blocking");

        northPanel.add(interruptibleButton);
        northPanel.add(blockingButton);

        interruptibleButton.addActionListener(event -> {
            interruptibleButton.setEnabled(false);
            blockingButton.setEnabled(false);
            cancelButton.setEnabled(true);
            connectThread = new Thread(() -> {
                try {
                    connectInterruptibly();
                } catch (IOException e) {
                    messages.append("\nInterruptibleSocketTest.connectInterruptibly: " + e);
                }
            });
            connectThread.start();
        });

        blockingButton.addActionListener(event -> {
            interruptibleButton.setEnabled(false);
            blockingButton.setEnabled(false);
            cancelButton.setEnabled(true);
            connectThread = new Thread(() -> {
                try {
                    connectBlocking();
                } catch (IOException e) {
                    messages.append("\nInterruptibleSocketTest.connectBlocking: " + e);
                }
            });
            connectThread.start();
        });

        cancelButton = new JButton("Cancel");
        cancelButton.setEnabled(false);
        northPanel.add(cancelButton);
        cancelButton.addActionListener(event -> {
            connectThread.interrupt();
            cancelButton.setEnabled(false);
        });

        server = new TestServer();
        new Thread(server).start();
        pack();
    }

    // 使用interruptible I/O连接服务器，不会被输入输出阻塞，在接收完前10个数后也可以被interrupt
    public void connectInterruptibly() throws IOException {
        messages.append("Interruptible:\n");
        try (SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 8189))) {
            in = new Scanner(channel, "UTF-8");
            while (!Thread.currentThread().isInterrupted()) {
                messages.append("Reading ");
                if (in.hasNextLine()) {
                    String line = in.nextLine();
                    messages.append(line);
                    messages.append("\n");
                    if ("BYE".equals(line.trim())) {
                        break;
                    }
                }
            }
        } finally {
            EventQueue.invokeLater(() -> {
                messages.append("Channel closed\n");
                interruptibleButton.setEnabled(true);
                blockingButton.setEnabled(true);
                cancelButton.setEnabled(false);
            });
        }
    }

    // 使用blocking I/O连接服务器，会被输入输出阻塞，在接收完前10个数后就无法interrupt了，直到服务器返回BYE
    public void connectBlocking() throws IOException {
        messages.append("Blocking:\n");
        try (Socket s = new Socket("localhost", 8189)) {
            in = new Scanner(s.getInputStream(), "UTF-8");
            while (!Thread.currentThread().isInterrupted()) {
                messages.append("Reading ");
                if (in.hasNextLine()) {
                    String line = in.nextLine();
                    messages.append(line);
                    messages.append("\n");
                    if ("BYE".equals(line.trim())) {
                        break;
                    }
                }
            }
        } finally {
            EventQueue.invokeLater(() -> {
                messages.append("Socket closed\n");
                interruptibleButton.setEnabled(true);
                blockingButton.setEnabled(true);
                cancelButton.setEnabled(false);
            });
        }
    }

    class TestServer implements Runnable {

        @Override
        public void run() {
            try (ServerSocket s = new ServerSocket(8189)) {
                while (true) {
                    Socket incoming = s.accept();
                    new Thread(new TestServerHandler(incoming)).start();
                }
            } catch (IOException e) {
                messages.append("\nTestServer.run: " + e);
            }
        }

    }

    class TestServerHandler implements Runnable {

        private Socket incoming;
        private int counter;

        public TestServerHandler(Socket incoming) {
            this.incoming = incoming;
        }

        @Override
        public void run() {
            try {
                try {
                    OutputStream outStream = incoming.getOutputStream();
                    PrintWriter out = new PrintWriter(
                            new OutputStreamWriter(outStream, "UTF-8"),
                            true);
                    while (counter < 100) {
                        counter++;
                        if (counter <= 10) {
                            out.println(counter);
                        }
                        Thread.sleep(100);
                    }
                    out.println("BYE");
                } finally {
                    incoming.close();
                    messages.append("Closing server\n");
                }
            } catch (Exception e) {
                messages.append("\nTestServerHandler.run: " + e);
            }
        }

    }

}
