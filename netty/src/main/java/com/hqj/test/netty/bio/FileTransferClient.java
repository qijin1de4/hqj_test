package com.hqj.test.netty.bio;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

import static java.lang.System.out;

/**
 * 向服务端
 */
public class FileTransferClient {
    public static void main(String[] args) {

        try(final Socket socket = new Socket("jinmac.local", 55554)) {
            String file = "/Users/qijinhu/softwares/rocketmq-4.9.3-SNAPSHOT/lib/netty-all-4.1.65.Final.jar";
            try(final InputStream fileInputStream = new FileInputStream(file)) {
                try(final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {
                    byte[] buffer = new byte[4096];
                    int totalCount = 0;
                    int read = 0;
                    Long start = System.currentTimeMillis();
                    while((read = fileInputStream.read(buffer)) > 0){
                        totalCount += read;
                        dataOutputStream.write(buffer);
                        out.println("sent : "+read+" bytes");
                    }
                    out.println("sent total "+ totalCount + " bytes, in " + (System.currentTimeMillis() - start) + " mili seconds!" );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
