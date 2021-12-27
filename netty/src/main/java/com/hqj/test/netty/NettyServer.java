package com.hqj.test.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import static java.lang.System.out;

public class NettyServer {
    public static void main(String[] args) {
        final EventLoopGroup bossGroup = new NioEventLoopGroup();
        final EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            //服务器启动bootstrap
            final ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)  //设置线程队列的连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new HelloNettyServerHandler());
                        }
                    });

            //绑定端口，启动服务
            ChannelFuture cf = serverBootstrap.bind(44440).sync();

            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(cf.isSuccess()) {
                        out.println("端口绑定成功");
                    } else {
                        out.println("端口绑定失败" );
                    }
                }
            });

            out.println("Server is ready");

            //监听通道，并且在收到断开连接指令后关闭通道。
            cf.channel().closeFuture().sync();

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}