package com.lk.netty.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @auther: kim
 * @create: 2020-02-25 14:12
 * @description: netty的群聊系统客户端
 */
public class GroupchatClient {
    private final String host;
    private final int port;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public GroupchatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException, IOException {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            Bootstrap client = new Bootstrap();
            client.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new GroupchatClientHandler());

                        }
                    });
            ChannelFuture sync = client.connect(getHost(), getPort()).sync();
            Channel channel = sync.channel();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            for (; ; ) {
                channel.writeAndFlush(br.readLine() + "\r\n");
            }
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new GroupchatClient("127.0.0.1", 7000).run();
    }
}
