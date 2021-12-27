package com.hqj.test.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

import static java.lang.System.out;

public class TestHttpHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     *
     * @param ctx
     * @param msg 服务器与客户端通信的数据被封装成 HttpObject 对象
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if(msg instanceof HttpRequest) {

            HttpRequest request = (HttpRequest) msg;
            if("/favicon.ico".equals(request.getUri())) {
                out.println("请求favicon, 不做响应");
                return;
            }

            out.println(msg.getClass());
            out.println("客户端地址："+ ctx.channel().remoteAddress().toString());

            final ByteBuf buf = Unpooled.copiedBuffer("hello我是服务器".getBytes(StandardCharsets.UTF_8));

            final FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
            response.headers().set(HttpHeaderNames.CONTENT_ENCODING, StandardCharsets.UTF_8);

            ctx.writeAndFlush(response);
        }
    }
}
