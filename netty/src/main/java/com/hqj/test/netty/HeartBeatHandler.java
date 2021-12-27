package com.hqj.test.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch(event.state()) {
                case READER_IDLE:
                    System.out.println("超过3秒，读空闲");
                    break;
                case WRITER_IDLE:
                    System.out.println("超过5秒，写空闲");
                    break;
                case ALL_IDLE:
                    System.out.println("超过7秒，读写空闲");
                    break;
            }
            System.out.println(System.currentTimeMillis()/1000);
        }
    }
}
