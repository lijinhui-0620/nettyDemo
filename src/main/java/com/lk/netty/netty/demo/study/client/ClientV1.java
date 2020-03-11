package com.lk.netty.netty.demo.study.client;

import com.lk.netty.netty.demo.study.client.codec.*;
import com.lk.netty.netty.demo.study.common.order.OrderOperation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ExecutionException;

/**
 * @auther: kim
 * @create: 2020-03-05 12:07
 * @description: 直接写入Operation
 */
public class ClientV1 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Bootstrap client = new Bootstrap();
        client
                .channel(NioSocketChannel.class)
                .group(new NioEventLoopGroup())
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new OrderFrameDecoder());
                        pipeline.addLast(new OrderFrameEecoder());
                        pipeline.addLast(new OrderProtocalDecode());
                        pipeline.addLast(new OrderProtocalEncoder());
                        pipeline.addLast(new OperationToRequestMessageEncoder());
                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                    }
                });
        ChannelFuture sync = client.connect("127.0.0.1", 8090).sync();
        sync.channel().writeAndFlush(new OrderOperation(1002, "西红柿鸡蛋"));

        sync.channel().closeFuture().get();
    }
}
