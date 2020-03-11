package com.lk.netty.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @auther: kim
 * @create: 2020-02-25 11:11
 * @description: http服务器
 */
public class TestServer {

    public static final int PORT = 8010;

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class).childHandler(new TestServerInitializer());

            ChannelFuture future = server.bind(PORT).sync();
            future.addListener((ChannelFuture cf) -> {
                if (cf.isSuccess()) {
                    System.out.println("服务器启动成功，监听端口：" + PORT);
                }
            });
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
