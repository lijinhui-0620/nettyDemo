package com.lk.netty.netty.rpc.provider;

import com.lk.netty.netty.rpc.netty.NettyServer;

/**
 * @auther: kim
 * @create: 2020-03-01 10:33
 * @description:
 */
public class ServerBootStrap {
    public static void main(String[] args) {
        NettyServer.start(7000);
    }
}
