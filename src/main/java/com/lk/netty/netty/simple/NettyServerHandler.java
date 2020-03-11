package com.lk.netty.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.util.CharsetUtil;

import java.net.SocketAddress;

/**
 * @auther: kim
 * @create: 2020-02-24 12:36
 * @description:
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    EventLoopGroup eventExecutors = new DefaultEventLoopGroup(16);

    //读取数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server  ctx =" + ctx);
        System.out.println(msg.getClass());
        if (msg instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) msg;
            System.out.println("客户端发送消息：" + byteBuf.toString(CharsetUtil.UTF_8));
            System.out.println("客户端地址：" + ctx.channel().remoteAddress());
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端~", CharsetUtil.UTF_8));
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        System.out.println("接收到客户端连接:" + socketAddress);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
