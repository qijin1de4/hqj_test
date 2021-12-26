package com.hqj.test.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScatteringAndGatheringDemo {

    public static void main(String[] args) {

        try(ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {

            serverSocketChannel.socket().bind(new InetSocketAddress(55551));

            while(true) {

                System.out.println("Waiting for client.....");

                try(final SocketChannel socketChannel = serverSocketChannel.accept()) {

                    System.out.println("Here came a client.");

                    final ByteBuffer[] byteBuffers = new ByteBuffer[2];

                    byteBuffers[0] = ByteBuffer.allocate(5);
                    byteBuffers[1] = ByteBuffer.allocate(3);

                    int msgLength = 8;

                    int byteRead = 0;
                    while(byteRead < msgLength) {
                        byteRead += socketChannel.read(byteBuffers);
                        System.out.println("byteRead="+byteRead);
                        Arrays.stream(byteBuffers).map(byteBuffer -> "position=" + byteBuffer.position()+", limit=" + byteBuffer.limit())
                                .forEach(System.out::println);
                    }

                    // 反转，用于回显到socket客户端
                    Arrays.stream(byteBuffers).forEach(ByteBuffer::flip);

                    int byteWrite = 0;
                    while(byteWrite < msgLength) {
                        byteWrite += socketChannel.write(byteBuffers);
                        System.out.println("byteWrite="+byteWrite);
                        Arrays.stream(byteBuffers).map(byteBuffer -> "position=" + byteBuffer.position()+", limit=" + byteBuffer.limit())
                                .forEach(System.out::println);
                    }

                    // 将ByteBuffer复位
                    Arrays.stream(byteBuffers).forEach(ByteBuffer::clear);

                    System.out.println("byteRead="+byteRead+", byteWrite="+byteWrite);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
