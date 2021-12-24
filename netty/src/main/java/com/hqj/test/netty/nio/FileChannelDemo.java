package com.hqj.test.netty.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 通过FileChannel的
 * 1，输出到文件DEMO。
 * 2，从文件读入数据DEMO。
 */
public class FileChannelDemo {

    public static void main(String[] args) {

        String contents = "FileChannel内容测试。";

        System.out.println(System.getProperty("user.dir"));

        //从FileChannel输出到文件
        try(FileOutputStream outputStream = new FileOutputStream("./hqj_test/netty/target/fileChannelDemo.txt")) {
            try(FileChannel fileChannel = outputStream.getChannel()) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);

                buffer.put(contents.getBytes(StandardCharsets.UTF_8));

                buffer.flip();

                fileChannel.write(buffer);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        //从文件读入到FileChannel

        try(FileInputStream fileInputStream = new FileInputStream("./hqj_test/netty/target/fileChannelDemo.txt")) {
            File file = new File("./hqj_test/netty/target/fileChannelDemo.txt");
            try(FileChannel fileChannel = fileInputStream.getChannel()) {
              ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
              fileChannel.read(buffer);
              System.out.println(new String(buffer.array()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
