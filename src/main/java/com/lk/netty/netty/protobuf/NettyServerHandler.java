package com.lk.netty.netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @auther: kim
 * @create: 2020-02-24 12:36
 * @description:
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {
        System.out.println("收到【" + msg.getName() + "】的来信");
        StudentPOJO.Student stu = StudentPOJO.Student.newBuilder().setName("王五").build();
        ctx.writeAndFlush(stu);
    }
}
