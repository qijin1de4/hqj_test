package com.hqj.test.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import static java.lang.System.out;

/**
 *  一个可以实现如下功能的在线群聊服务器：
 *  1，用户上线，下线广播。
 *  2，用户消息广播。
 */
public class GroupChatServer {

    private Selector selector = null;

    public GroupChatServer() {

        try(final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()){
            serverSocketChannel.socket().bind(new InetSocketAddress("jinmac.local", 55553));
            serverSocketChannel.configureBlocking(false);
            this.selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while(true) {
                if(selector.select(1000) == 0) {
                    out.println("等待客户连接1秒钟!");
                    continue;
                }

                final Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while(iterator.hasNext()) {
                    final SelectionKey key = iterator.next();
                    if(key.isAcceptable()) {
                        SocketChannel channel = serverSocketChannel.accept();
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(512));
                        String message = channel.getLocalAddress()+"上线了";
                        out.println(message);
                        notifyOtherClients(message, channel);
                    }

                    if(key.isReadable()) {
                        readDataFromClient(key);
                    }

                    iterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取客户端发送的信息并将其广播到所有其他客户端
     * @param key
     */
    private void readDataFromClient(SelectionKey key) throws IOException {

        final SocketChannel socketChannel = (SocketChannel) key.channel();
        try{
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            int read = socketChannel.read(buffer);
            if(read > 0) {
                String msg = socketChannel.getLocalAddress()+" said: " + new String(buffer.array(), 0, buffer.position());
                out.println(msg);
                //通知其他客户端
                notifyOtherClients(msg, socketChannel);
            }
            buffer.clear();
        } catch(IOException e) {
            String offlineMsg = socketChannel.getLocalAddress()+"离线了!";
            out.println(offlineMsg);
            //取消注册
            key.cancel();
            //关闭通道
            socketChannel.close();
            notifyOtherClients(offlineMsg,  socketChannel);
        }

    }

    /**
     * 通知其他客户端
     * @param msg
     * @param selfChannel
     */
    private void notifyOtherClients(String msg, SocketChannel selfChannel) throws IOException {
        for(SelectionKey key : selector.keys()) {

            final SelectableChannel targetChannel = key.channel();

            if(targetChannel instanceof SocketChannel && targetChannel != selfChannel) {
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
                ((SocketChannel) targetChannel).write(buffer);
            }

        }

    }

    public static void main(String[] args) {
        final GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.hashCode();
    }
}
