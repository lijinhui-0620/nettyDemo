package com.lk.netty.netty.demo.study.client.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @auther: kim
 * @create: 2020-03-05 11:39
 * @description:
 */
public class OrderFrameDecoder extends LengthFieldBasedFrameDecoder {
    public OrderFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}
