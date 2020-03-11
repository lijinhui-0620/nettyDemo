package com.lk.netty.netty.demo.study.server;


import com.lk.netty.netty.demo.study.server.codec.OrderFrameDecoder;
import com.lk.netty.netty.demo.study.server.codec.OrderFrameEecoder;
import com.lk.netty.netty.demo.study.server.codec.OrderProtocalDecode;
import com.lk.netty.netty.demo.study.server.codec.OrderProtocalEncoder;
import com.lk.netty.netty.demo.study.server.codec.handler.AuthHandler;
import com.lk.netty.netty.demo.study.server.codec.handler.MetricHandler;
import com.lk.netty.netty.demo.study.server.codec.handler.OrderServerProcessHandler;
import com.lk.netty.netty.demo.study.server.codec.handler.ServerIdelCheckHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ipfilter.IpFilterRuleType;
import io.netty.handler.ipfilter.IpSubnetFilterRule;
import io.netty.handler.ipfilter.RuleBasedIpFilter;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.traffic.GlobalTrafficShapingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutionException;

import static com.sun.javafx.PlatformUtil.isLinux;

/**
 * @auther: kim
 * @create: 2020-03-05 11:50
 * @description:
 */
public class Server {
    public static void main(String[] args) throws InterruptedException, ExecutionException, CertificateException,
            SSLException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //连接数统计
        MetricHandler metricHandler = new MetricHandler();
        //业务线程
        UnorderedThreadPoolEventExecutor eventExecutors = new UnorderedThreadPoolEventExecutor(10,
                new DefaultThreadFactory("business"));

        //流量整形
        GlobalTrafficShapingHandler globalTrafficShapingHandler =
                new GlobalTrafficShapingHandler(new NioEventLoopGroup(), 100 * 1024 * 1024, 100 * 1024 * 1024);
        //ip黑白名单
        IpSubnetFilterRule ipSubnetFilterRule = new IpSubnetFilterRule("127.0.0.1", 8,
                IpFilterRuleType.ACCEPT);
        RuleBasedIpFilter ruleBasedIpFilter = new RuleBasedIpFilter(ipSubnetFilterRule);
        //ssl
        SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
        System.out.println(selfSignedCertificate.certificate());
        SslContext build = SslContextBuilder.forServer(selfSignedCertificate.certificate(),
                selfSignedCertificate.privateKey()).build();

        AuthHandler authHandler = new AuthHandler();
        EventLoopGroup bossGroup;
        EventLoopGroup workerGroup;

        if (isLinux()) {//是linux系统就是用epollEventLoopGroup
            bossGroup = new EpollEventLoopGroup(2);
            workerGroup = new EpollEventLoopGroup();
        } else {//否则使用通用的nioEventLoopGroup
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
        }
        try {
            serverBootstrap.handler(new LoggingHandler(LogLevel.INFO))
                    .channel(NioServerSocketChannel.class)
                    .group(bossGroup, workerGroup)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //连接统计
                            pipeline.addLast("metricsHandler", metricHandler);
                            //ip黑白名单
                            pipeline.addLast("ipfilter", ruleBasedIpFilter);
                            //流量整形
                            pipeline.addLast("globalTrafficShapingHandler", globalTrafficShapingHandler);
                            //空闲检测
                            pipeline.addLast("serverIdelCheckHandler", new ServerIdelCheckHandler());
                            //成套出现的编解码处理器
                            pipeline.addLast(new OrderFrameDecoder());
                            pipeline.addLast(new OrderFrameEecoder());
                            pipeline.addLast(new OrderProtocalDecode());
                            pipeline.addLast(new OrderProtocalEncoder());
                            //业务处理器
                            pipeline.addLast(authHandler);
                            pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                            pipeline.addLast(eventExecutors,
                                    new OrderServerProcessHandler());
                        }
                    });
            ChannelFuture sync = serverBootstrap.bind(8090).sync();

            sync.channel().closeFuture().get();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
