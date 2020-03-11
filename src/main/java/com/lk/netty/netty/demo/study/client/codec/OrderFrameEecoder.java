package com.lk.netty.netty.demo.study.client.codec;

import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @auther: kim
 * @create: 2020-03-05 11:39
 * @description:
 */
public class OrderFrameEecoder extends LengthFieldPrepender {
    public OrderFrameEecoder() {
        super(2);
    }
}
