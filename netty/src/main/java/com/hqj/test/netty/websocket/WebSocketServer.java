package com.hqj.test.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServer {
    public static void main(String[] args) {
        final EventLoopGroup bossGroup = new NioEventLoopGroup();
        final EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            final ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // HTTP协议的编码器，解码器
                            ch.pipeline().addLast(new HttpServerCodec());
                            // 以块的方式写数据
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            /**
                             * HTTP协议在传输大量数据的，会进行多次HTTP请求，进行分段传输。
                             * HttpObjectAggregator就是将多个段聚合为一个整体。
                             */
                            ch.pipeline().addLast(new HttpObjectAggregator(8192));

                            //ws://jinmcal.local:4443/hello
                            //WebSocket以帧为单位进行通信
                            //WebSocketServerProtocolHandler将HTTP协议升级为WebSocket协议
                            ch.pipeline().addLast(new WebSocketServerProtocolHandler("/hello"));

                            ch.pipeline().addLast(new MyTextWebSocketFrameHandler());
                        }
                    });

            ChannelFuture cf = bootstrap.bind(4443).sync();

            cf.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
