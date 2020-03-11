package com.lk.netty.netty.protobuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @auther: kim
 * @create: 2020-02-24 12:51
 * @description:
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap client = new Bootstrap();

            client.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline ph = ch.pipeline();
                            ph.addLast(new ProtobufVarint32FrameDecoder());
                            ph.addLast(new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));
                            ph.addLast(new ProtobufVarint32LengthFieldPrepender());
                            ph.addLast(new ProtobufEncoder());
                            ph.addLast(new NettyClientHnadler());
                        }
                    });
            System.out.println("客户端 ok");
            ChannelFuture sync = client.connect("127.0.0.1", 6666).sync();
            sync.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }
}
