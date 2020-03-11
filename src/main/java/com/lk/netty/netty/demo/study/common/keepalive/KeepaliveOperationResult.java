package com.lk.netty.netty.demo.study.common.keepalive;

import com.lk.netty.netty.demo.study.common.OperationResult;

/**
 * @auther: kim
 * @create: 2020-03-05 11:17
 * @description:
 */
public class KeepaliveOperationResult extends OperationResult {

    private final long time;


    public KeepaliveOperationResult(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
