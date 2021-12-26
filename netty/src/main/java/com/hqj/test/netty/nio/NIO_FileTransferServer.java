package com.hqj.test.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import static java.lang.System.out;

public class NIO_FileTransferServer {

    public static void main(String[] args) {
        try(final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.socket().bind(new InetSocketAddress("jinmac.local", 55556));
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while(true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                int read, totalCount = 0;
                while((read = socketChannel.read(buffer)) > 0){
                    totalCount += read;
                    out.println("read :" + read + " bytes!");
                    buffer.rewind();
                }
                out.println("total read : " + totalCount + " bytes!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
