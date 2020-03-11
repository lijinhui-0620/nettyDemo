package com.lk.netty.netty.demo.study.common;

/**
 * @auther: kim
 * @create: 2020-03-05 11:04
 * @description:
 */
public class ResponseMessage extends Message<OperationResult> {
    @Override
    public Class getMessageBodyDecodeClass(int opCode) {
        return OperationType.fromOpCode(opCode).getOperationResultClazz();
    }

    public static void main(String[] args) {
        System.out.println(OperationType.fromOpCode(3).getOperationResultClazz());
    }
}
