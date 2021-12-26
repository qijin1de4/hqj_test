package com.hqj.test.netty.bio;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out;

/**
 * 文件服务器，将客户端传来的文件存储到文件服务器上
 */
public class FileTransferServer {
    public static void main(String[] args) throws IOException {
        try(final ServerSocket serverSocket = new ServerSocket(55554)) {

            while(true) {
                final Socket socket = serverSocket.accept();
                try(final DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())) {
                    int totalCount = 0;
                    byte[] buffer = new byte[1024];
                    int read = 0;
                    while( (read = dataInputStream.read(buffer)) > 0) {
                        out.println("read : "+read+" bytes");
                        totalCount += read;
                    }
                    out.println("read total : " + totalCount + " bytes!");
                } finally {
                    socket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
