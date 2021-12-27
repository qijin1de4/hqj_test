package com.hqj.test.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


public class MyChannelHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器
        //HttpServerCodec是netty提供的http编/解码器
        ch.pipeline().addLast("MyHttpServerCodec", new HttpServerCodec());

        //增加自定义处理器
        ch.pipeline().addLast("MyHttpServerHandler", new TestHttpHandler());

    }
}
