package com.hqj.test.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIO_Server {

    public static void main(String[] args) {

        try(final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {

            serverSocketChannel.socket().bind(new InetSocketAddress(55552));

            final Selector selector = Selector.open();

            //设置为非阻塞
            serverSocketChannel.configureBlocking(false);

            //将服务器channel注册到seletor，关心事件为Accept
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while(true) {
                if(selector.select(1000) == 0) { // 等待1秒，没有事件发生。
                    System.out.println("等待1秒!");
                    continue;
                }

                final Set<SelectionKey> selectedKeys = selector.selectedKeys();

                final Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {

                    final SelectionKey key = keyIterator.next();

                    if(key.isAcceptable()) { // 如果是连接事件
                        //服务器接受客户端连接请求，并生成一个SocketChannel
                        final SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        System.out.println("client [localAddr:" + socketChannel.getLocalAddress() + ", remoteAddr:"+socketChannel.getRemoteAddress()+"] connected!");
                        //将刚接受的客户端的SocketChannel也注册到selector上, 关注该通道的读事件
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(512));
                    }

                    if(key.isReadable()) {
                        final SocketChannel socketChannel = (SocketChannel) key.channel();

                        ByteBuffer buffer = (ByteBuffer) key.attachment(); //获取到channel关联的buffer
                        int read = socketChannel.read(buffer); //读取channel中内容到buffer
                        if(read > 0) {
                            System.out.println("from client["+socketChannel.hashCode()+"] : "+new String(buffer.array(), 0, buffer.position()));
                        }
                        buffer.clear();
                    }
                    //从selectionkey集合中删除该selectionkey，防止重复操作
                    keyIterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
