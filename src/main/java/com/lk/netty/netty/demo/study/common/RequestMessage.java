package com.lk.netty.netty.demo.study.common;

/**
 * @auther: kim
 * @create: 2020-03-05 11:04
 * @description:
 */
public class RequestMessage extends Message<Operation> {
    @Override
    public Class getMessageBodyDecodeClass(int opCode) {
        return OperationType.fromOpCode(opCode).getOperationClazz();
    }

    public RequestMessage(long streamId, Operation operation) {

        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setStreamId(streamId);
        messageHeader.setOpCode(OperationType.fromOperation(operation).getOpCode());
        setMessageHeader(messageHeader);
        setMessageBody(operation);
    }

    public RequestMessage() {
    }

}
