package com.lk.netty.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @auther: kim
 * @create: 2020-02-25 13:48
 * @description:
 */
public class GroupchatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个channel组，用于管理所有的channel
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final String PREFIX = "【服务器】-";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("from 【" + ctx.channel().remoteAddress() + "】");
        Channel channel = ctx.channel();
        //这时候需要变量channelGroup 处理自己的情况
        channels.forEach(ch -> {
            if (ch != channel) {
                ch.writeAndFlush("用户【" + channel.remoteAddress() + "】[" + LocalDateTime.now().format(dtf) + "]->" + msg + "\n");
            } else {
                ch.writeAndFlush("【自己】[" + LocalDateTime.now().format(dtf) + "]->" + msg + "\n");
            }
        });

    }

    //建立连接后第一个被回调的方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.writeAndFlush(PREFIX + channel.remoteAddress() + " 加入聊天室\n");
        channels.add(channel);
    }

    //表示channel处于活动的状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 下线了");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.writeAndFlush(PREFIX + channel.remoteAddress() + " 离开了聊天室\n");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
