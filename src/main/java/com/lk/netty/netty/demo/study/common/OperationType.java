package com.lk.netty.netty.demo.study.common;

import com.lk.netty.netty.demo.study.common.auth.AuthOperation;
import com.lk.netty.netty.demo.study.common.auth.AuthOperationResult;
import com.lk.netty.netty.demo.study.common.keepalive.KeepaliveOperation;
import com.lk.netty.netty.demo.study.common.keepalive.KeepaliveOperationResult;
import com.lk.netty.netty.demo.study.common.order.OrderOperation;
import com.lk.netty.netty.demo.study.common.order.OrderOperationResult;

import java.util.function.Predicate;

/**
 * @auther: kim
 * @create: 2020-03-05 11:04
 * @description: 操作类型
 */
public enum OperationType {

    AUTH(1, AuthOperation.class, AuthOperationResult.class),
    KEEPALIVE(2, KeepaliveOperation.class, KeepaliveOperationResult.class),
    ORDER(3, OrderOperation.class, OrderOperationResult.class);

    private int opCode;
    private Class<? extends Operation> operationClazz;
    private Class<? extends OperationResult> operationResultClazz;

    OperationType(int opCode, Class<? extends Operation> operationClazz,
                  Class<? extends OperationResult> responseClass) {
        this.opCode = opCode;
        this.operationClazz = operationClazz;
        this.operationResultClazz = responseClass;
    }

    public int getOpCode() {
        return opCode;
    }

    public Class<? extends Operation> getOperationClazz() {
        return operationClazz;
    }

    public Class<? extends OperationResult> getOperationResultClazz() {
        return operationResultClazz;
    }

    public static OperationType fromOpCode(int type) {
        return getOperationType(requestType -> requestType.opCode == type);
    }

    public static OperationType fromOperation(Operation operation) {
        return getOperationType(requestType -> requestType.operationClazz == operation.getClass());
    }

    private static OperationType getOperationType(Predicate<OperationType> predicate) {
        OperationType[] values = values();
        for (OperationType operationType : values) {
            if (predicate.test(operationType)) {
                return operationType;
            }
        }

        throw new AssertionError("no found type");
    }
}
