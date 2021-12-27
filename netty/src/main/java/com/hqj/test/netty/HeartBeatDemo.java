package com.hqj.test.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 一个心跳检测DEMO程序
 * 当一个客户端连接服务器后：
 * 3s ： 客户端向服务器没有读操作，触发读空闲事件
 * 5s ： 服务器没有向客户端进行写操作，触发写空闲事件
 * 7s ： 客户端与服务器之间没有读写操作，触发读写空闲事件
 */
public class HeartBeatDemo {
    public static void main(String[] args) {
        final EventLoopGroup bossGroup = new NioEventLoopGroup();
        final EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            final ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.INFO)) //添加日志记录handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //空闲状态触发器
                            // 如果 3s没有读操作，或者 5秒没有写操作，或者7秒没读写操作。
                            // 就会触发相应的事件给后续的handler。
                            ch.pipeline().addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));

                            ch.pipeline().addLast(new HeartBeatHandler());
                        }
                    });

            ChannelFuture cf = serverBootstrap.bind(4442).sync();

            cf.channel().closeFuture().sync();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
