package com.lk.netty.netty.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @auther: kim
 * @create: 2020-02-24 12:17
 * @description:
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap server = new ServerBootstrap();

        //m默认 cpu核心* 2
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列等待连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline ph = ch.pipeline();
                            ph.addLast(new ProtobufVarint32FrameDecoder());
                            ph.addLast(new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));
                            ph.addLast(new ProtobufVarint32LengthFieldPrepender());
                            ph.addLast(new ProtobufEncoder());
                            ph.addLast(new NettyServerHandler());
                        }
                    })//给workergroup的EventLoop对应的管道设置处理器
            ;
            ChannelFuture sync = server.bind(6666).sync();
            sync.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {

                }
            });
            Channel ch = sync.channel();
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
