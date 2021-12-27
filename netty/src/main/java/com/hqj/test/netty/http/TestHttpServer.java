package com.hqj.test.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestHttpServer {
    public static void main(String[] args) {

        final EventLoopGroup serverGruop = new NioEventLoopGroup();
        final EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            final ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(serverGruop, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 124)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new MyChannelHandlerInitializer());
            ChannelFuture cf = serverBootstrap.bind( 4441).sync();
            //等待连接与关闭事件
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            serverGruop.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
