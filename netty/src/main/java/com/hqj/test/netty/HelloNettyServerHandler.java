package com.hqj.test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import static java.lang.System.out;

public class HelloNettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        out.println("context : " + ctx);

        ByteBuf buf = (ByteBuf)msg;

        out.println("message from client : \n"+buf.toString(CharsetUtil.UTF_8));

//        out.println("client address : " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //给客户端输出
//        ctx.writeAndFlush(Unpooled.copiedBuffer("hello from server", CharsetUtil.UTF_8));
    }

    // 处理异常， 一般需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
