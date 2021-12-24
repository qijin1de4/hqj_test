package com.hqj.test.netty.nio;

import java.nio.IntBuffer;

public class BasicBuffer {

    public static void main(String[] args) {

        IntBuffer buffer = IntBuffer.allocate(5);

        buffer.put(10);
        buffer.put(11);
        buffer.put(12);
        buffer.put(13);
        buffer.put(14);

        //将buffer倒转，实现读写切换
        buffer.flip();

        while(buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }

    }
}
