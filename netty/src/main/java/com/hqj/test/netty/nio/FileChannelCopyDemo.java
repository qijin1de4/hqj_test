package com.hqj.test.netty.nio;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * 用FileChannel实现文件copy
 *
 */
public class FileChannelCopyDemo {

    public static void main(String[] args) {

        String srcFile = "/Users/qijinhu/softwares/rocketmq-4.9.3-SNAPSHOT/lib/netty-all-4.1.65.Final.jar";

        String destFile = "./hqj_test/netty/target/netty-all-4.1.65.Final.jar";

        try(FileInputStream fileInputStream = new FileInputStream(srcFile)) {

            try(FileOutputStream fileOutputStream = new FileOutputStream(destFile)) {
                    FileChannel inputFileChannel = fileInputStream.getChannel();
                    FileChannel outputFileChannel = fileOutputStream.getChannel();
                    try {
                        ByteBuffer buffer = ByteBuffer.allocate(512);
                        while(true) {
                            buffer.clear();
                            if( -1 == inputFileChannel.read(buffer)) {
                                break;
                            }
                            buffer.flip();
                            outputFileChannel.write(buffer);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        inputFileChannel.close();
                        outputFileChannel.close();
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
