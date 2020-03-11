package com.lk.netty.netty.demo.study.client.codec.dispatcher;

import io.netty.handler.timeout.IdleStateHandler;

/**
 * @auther: kim
 * @create: 2020-03-06 11:53
 * @description:
 */
public class ClientIdleCheckHandler extends IdleStateHandler {
    public ClientIdleCheckHandler() {
        super(0, 5, 0);
    }
}
