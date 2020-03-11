package com.lk.netty.netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @auther: kim
 * @create: 2020-02-24 13:02
 * @description:
 */
public class NettyClientHnadler extends ChannelInboundHandlerAdapter {


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof StudentPOJO.Student) {
            StudentPOJO.Student stu = (StudentPOJO.Student) msg;
            System.out.println("【服务器】"+stu);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        StudentPOJO.Student stu = StudentPOJO.Student.newBuilder().setId(1).setName("张三").build();
        ctx.writeAndFlush(stu);
    }
}
