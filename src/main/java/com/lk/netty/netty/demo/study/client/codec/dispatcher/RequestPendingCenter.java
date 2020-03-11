package com.lk.netty.netty.demo.study.client.codec.dispatcher;

import com.lk.netty.netty.demo.study.common.OperationResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther: kim
 * @create: 2020-03-05 12:17
 * @description: 请求处理中心  匹配处理结果
 */
public class RequestPendingCenter {

    private Map<Long, OperationResultFuture> map = new ConcurrentHashMap();

    public void put(Long streamId, OperationResultFuture future) {
        this.map.put(streamId, future);
    }

    public void set(Long streamId, OperationResult operationResult) {
        OperationResultFuture operationResultFuture = this.map.get(streamId);
        if (operationResultFuture != null) {
            operationResultFuture.setSuccess(operationResult);
            this.map.remove(streamId);
        }
    }
}
