package com.hqj.test.netty.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 *  内存映射ByteBuffer
 *  通过映射堆外内存的方式修改文件内容。
 *
 */
public class MappedByteBufferDemo {

    public static void main(String[] args) {

        try(RandomAccessFile file = new RandomAccessFile("./hqj_test/netty/target/fileChannelDemo.txt", "rw")) {
            FileChannel channel = file.getChannel();

            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

            buffer.put(0, (byte) 'f');
            buffer.put(4, (byte) 'c');

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
