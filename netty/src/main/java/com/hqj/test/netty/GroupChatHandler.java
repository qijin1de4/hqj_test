package com.hqj.test.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import static java.lang.System.out;

public class GroupChatHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        out.println("客户端["+ctx.channel().remoteAddress()+"] 上线\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        out.println(ctx.channel().remoteAddress()+"离线了!\n");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("客户端【"+ctx.channel().remoteAddress()+"】加入聊天!\n");
        //加入监听的channelGroup
        channelGroup.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("客户端【"+ctx.channel().remoteAddress()+"】离开了!\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        channelGroup.forEach(ch -> {
            if(ch != ctx.channel()) {
                ch.writeAndFlush(ctx.channel().remoteAddress()+"说：" + msg+"\n");
            }
        });
    }
}
