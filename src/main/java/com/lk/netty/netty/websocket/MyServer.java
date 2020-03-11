package com.lk.netty.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import static com.sun.javafx.PlatformUtil.isLinux;

/**
 * @auther: kim
 * @create: 2020-02-26 10:10
 * @description: webSocket
 */
public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup;
        EventLoopGroup workerGroup;
        if (isLinux()) {//是linux系统就是用epollEventLoopGroup
            bossGroup = new EpollEventLoopGroup(1);
            workerGroup = new EpollEventLoopGroup();
        } else {//否则使用通用的nioEventLoopGroup
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
        }

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childOption(NioChannelOption.SO_KEEPALIVE, true)
                    .option(NioChannelOption.SO_BACKLOG, 128)
                    .childHandler(new WebSocketHandlerInitializer());
            System.out.println("netty 服务器启动");
            ChannelFuture sync = server.bind(7000).sync();
            sync.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
