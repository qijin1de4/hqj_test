package com.hqj.test.netty.nio;

import java.io.LineNumberReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NIO_Client {
    public static void main(String[] args) {
        try(SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.configureBlocking(false);
            final InetSocketAddress inetSocketAddress = new InetSocketAddress("jinmac.local", 55552);
            if(!socketChannel.connect(inetSocketAddress)) {
                while(!socketChannel.finishConnect()) {
                    System.out.println("连接中，客户端不会阻塞，可以做其他事情！");
                }
            }
            ByteBuffer buffer = ByteBuffer.wrap(("client["+socketChannel.hashCode()+"] says: welcome to earth!").getBytes(StandardCharsets.UTF_8));
            //发送数据到server
            socketChannel.write(buffer);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
