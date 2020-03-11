package com.lk.netty.netty.demo.study.client;

import com.lk.netty.netty.demo.study.client.codec.*;
import com.lk.netty.netty.demo.study.client.codec.dispatcher.OperationResultFuture;
import com.lk.netty.netty.demo.study.client.codec.dispatcher.RequestPendingCenter;
import com.lk.netty.netty.demo.study.client.codec.dispatcher.ResponseDispatcherHandler;
import com.lk.netty.netty.demo.study.common.OperationResult;
import com.lk.netty.netty.demo.study.common.RequestMessage;
import com.lk.netty.netty.demo.study.common.order.OrderOperation;
import com.lk.netty.netty.demo.study.util.IdUtil;
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
 * @description: 获取请求结果
 */
public class ClientV2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Bootstrap client = new Bootstrap();
        RequestPendingCenter requestPendingCentor = new RequestPendingCenter();
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
                        pipeline.addLast(new ResponseDispatcherHandler(requestPendingCentor));
                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                    }
                });
        ChannelFuture sync = client.connect("127.0.0.1", 8090).sync();

        long stramId = IdUtil.nextId();
        OperationResultFuture operationResultFuture = new OperationResultFuture();
        requestPendingCentor.put(stramId, operationResultFuture);
        RequestMessage requestMessage = new RequestMessage(stramId, new OrderOperation(1001, "黄焖鸡"));
        sync.channel().writeAndFlush(requestMessage);
        //阻塞获取请求结果
        OperationResult operationResult = operationResultFuture.get();
        System.out.println(operationResult);
        sync.channel().closeFuture().get();
    }
}
