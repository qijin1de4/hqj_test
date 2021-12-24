package com.hqj.test.netty.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模拟一个TCP服务器监听55550端口。
 *
 * 可以通过nc或者telnet连接到服务器，并输入字符进行测试
 * nc host port
 */
public class BIO_Server {

    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(55550);

        System.out.println("Sever listen on 55550");

        while(true) {

            System.out.println("Waiting for client......");
            final Socket socket = serverSocket.accept();

            System.out.println("Accepted a client.");

            executorService.execute(() -> {
                try {

                    String threaInfo = Thread.currentThread().getName() + "[" + Thread.currentThread().getId() + "] received : ";

                    InputStream inputStream = socket.getInputStream();
                    byte[] bytes = new byte[1024];
                    while(true) {
                        System.out.println("Waitting for read......");
                        int read = inputStream.read(bytes);
                        if(read != -1) {
                            System.out.println( threaInfo + new String(bytes, 0, read));
                        } else {
                            break;
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    }catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }

    }
}
