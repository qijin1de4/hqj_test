package com.hqj.test.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) {
        final EventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new HelloNettyClientHandler());
                        }
                    });

            //启动client连接服务器
            ChannelFuture cf = bootstrap.connect("jinmac.local", 44440).sync();

            //监听通道，如果连接断开则关闭
            cf.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
