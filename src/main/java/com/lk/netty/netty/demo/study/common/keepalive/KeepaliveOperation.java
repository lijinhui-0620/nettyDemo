package com.lk.netty.netty.demo.study.common.keepalive;

import com.lk.netty.netty.demo.study.common.Operation;

/**
 * @auther: kim
 * @create: 2020-03-05 11:17
 * @description:
 */
public class KeepaliveOperation extends Operation {
    private long time;

    public KeepaliveOperation() {
        this.time = System.nanoTime();
    }

    @Override
    public KeepaliveOperationResult execute() {
        System.out.println("空闲 客户端发送keepalive 保持连接");
        KeepaliveOperationResult orderResponse = new KeepaliveOperationResult(time);
        return orderResponse;
    }
}
