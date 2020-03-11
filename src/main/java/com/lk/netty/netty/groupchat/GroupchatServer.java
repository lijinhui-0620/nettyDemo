package com.lk.netty.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @auther: kim
 * @create: 2020-02-25 13:36
 * @description: 简单的群聊系统服务器实现
 */
public class GroupchatServer {
    private final int port;

    public GroupchatServer(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void run() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new GroupchatServerHandler());
                        }
                    });

            ChannelFuture sync = server.bind(getPort()).sync();
            sync.addListener((future) -> {
                if (future.isSuccess()) {
                    System.out.println("服务器启动成功，监听端口：" + getPort());
                } else if (future.cause() != null) {
                    System.out.println("服务器启动失败");
                    future.cause().printStackTrace();
                }
            });
            sync.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new GroupchatServer(7000).run();
    }
}
