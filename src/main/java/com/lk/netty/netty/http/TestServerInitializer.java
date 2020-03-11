package com.lk.netty.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @auther: kim
 * @create: 2020-02-25 11:12
 * @description: handler初始化
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("httpServerCodec", new HttpServerCodec());//netty提供的处理http的编解码器
        pipeline.addLast("TestHttpServerHandler", new TestHttpServerHandler());
        System.out.println("初始化完成");
    }
}
