package com.hqj.test.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class GroupChatClient {

    private Selector selector = null;

    private SocketChannel socketChannel = null;

    private String name;

    public GroupChatClient() throws IOException {

        try{
            this.selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress("jinmac.local", 55553));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(512));
            this.name = socketChannel.getLocalAddress().toString();
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    private void sendMesasge(String msg) {
        try{
            String message = name + " said : " + msg;
            socketChannel.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readMessage() {
        try {
            if(selector.select(1000) > 0) { //
                final Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while(iterator.hasNext()) {
                    final SelectionKey key = iterator.next();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    int read = socketChannel.read(buffer);
                    if(read > 0) {
                        out.println(new String(buffer.array(), 0, buffer.position()));
                    }
                    buffer.clear();
                    //删除该key，防止重复
                    iterator.remove();
                }
            } else {
//                out.println("没有可用channel");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws  Exception {
        GroupChatClient client = new GroupChatClient();

        new Thread( () -> {
            try{
                while(true) {
                    client.readMessage();
                    TimeUnit.SECONDS.sleep(1);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        } ).start();

        //发送数据给服务器
        final Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            client.sendMesasge(msg);
        }
    }
}
