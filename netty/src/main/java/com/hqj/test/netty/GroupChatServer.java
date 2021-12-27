package com.hqj.test.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 实现一个基于Netty的群聊服务器
 */
public class GroupChatServer {

    public static void main(String[] args) {
        final EventLoopGroup bossGroup = new NioEventLoopGroup();
        final EventLoopGroup workerGruop = new NioEventLoopGroup();
        try {

            final ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGruop)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("decoder", new StringDecoder());
                            ch.pipeline().addLast("encoder", new StringEncoder());
                            ch.pipeline().addLast(new GroupChatHandler());
                        }
                    });

            ChannelFuture cf = serverBootstrap.bind(4442).sync();

            cf.channel().closeFuture().sync();

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGruop.shutdownGracefully();
        }
    }
}
