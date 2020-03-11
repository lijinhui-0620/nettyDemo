package com.lk.netty.netty.demo.study.common;

/**
 * @auther: kim
 * @create: 2020-03-05 11:04
 * @description:
 */
public class MessageHeader {
    /**
     * 版本号
     */
    private int version = 1;
    /**
     * 请求id
     */
    private Long streamId;
    /**
     * 请求代码
     */
    private int opCode;

    public MessageHeader(int version, Long streamId, int opCode) {
        this.version = version;
        this.streamId = streamId;
        this.opCode = opCode;
    }

    public MessageHeader() {
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getStreamId() {
        return streamId;
    }

    public void setStreamId(Long streadId) {
        this.streamId = streadId;
    }

    public int getOpCode() {
        return opCode;
    }

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }

    @Override
    public String toString() {
        return "MessageHeader{" +
                "version=" + version +
                ", streamId=" + streamId +
                ", opCode=" + opCode +
                '}';
    }
}
