package com.lk.netty.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @auther: kim
 * @create: 2020-02-26 17:02
 * @description:
 */
public class WebSocketHandlerInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //websocket是基于http协议，所以要使用http的编解码器
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());
        pipeline.addLast("ChunkedWriteHandler", new ChunkedWriteHandler());
        //http数据再传输过程中是分段的，HttpObjectAggregator的作用是将多个段聚合
        pipeline.addLast("HttpObjectAggregator", new HttpObjectAggregator(8192));
        //WebSocketServerProtocolHandler 核心功能是将构造参数传入的地址 从http协议升级为ws协议，保持长连接
        pipeline.addLast("WebSocketServerProtocolHandler", new WebSocketServerProtocolHandler(
                "/ws", null, false, 65536 * 100));
        pipeline.addLast("myHandler", new MyWebSocketFrameHandler());
    }
}
