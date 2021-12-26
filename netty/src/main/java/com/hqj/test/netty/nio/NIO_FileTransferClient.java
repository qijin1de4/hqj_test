package com.hqj.test.netty.nio;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

import static java.lang.System.out;

public class NIO_FileTransferClient {
    public static void main(String[] args) {
        try(final SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("jinmac.local", 55556))) {
            String file = "/Users/qijinhu/softwares/rocketmq-4.9.3-SNAPSHOT/lib/netty-all-4.1.65.Final.jar";
            try(final FileChannel fileChannel = new FileInputStream(file).getChannel()) {
                long start = System.currentTimeMillis();
                // linux下一个transferTO就可以实现从fileChannel到socketChannel的零拷贝复制。
                // windows下一次transferTo操作最多可以复制8M数据，大于8M的文件需要做拆分多次transferTo
                long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
                out.println("sent total "+ transferCount + " bytes, in " + (System.currentTimeMillis() - start) + " mili seconds!" );
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
