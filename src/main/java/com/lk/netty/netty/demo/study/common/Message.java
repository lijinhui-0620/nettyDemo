package com.lk.netty.netty.demo.study.common;

import com.lk.netty.netty.demo.study.util.JsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @auther: kim
 * @create: 2020-03-05 11:03
 * @description:
 */
public abstract class Message<T extends MessageBody> {
    private MessageHeader messageHeader;
    private T messageBody;

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public T getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(T messageBody) {
        this.messageBody = messageBody;
    }

    public void encode(ByteBuf byteBuf) {
        MessageHeader messageHeader = getMessageHeader();
        byteBuf.writeInt(messageHeader.getVersion());
        byteBuf.writeLong(messageHeader.getStreamId());
        byteBuf.writeInt(messageHeader.getOpCode());
        byteBuf.writeBytes(JsonUtil.toJson(getMessageBody()).getBytes(CharsetUtil.UTF_8));
    }

    public abstract Class<T> getMessageBodyDecodeClass(int opCode);

    public void decode(ByteBuf msg) {
        int version = msg.readInt();
        long stramId = msg.readLong();
        int opCode = msg.readInt();
        MessageHeader messageHeader = new MessageHeader(version, stramId, opCode);
        setMessageHeader(messageHeader);
        Class<T> bodyClazz = getMessageBodyDecodeClass(opCode);
        T body = JsonUtil.fromJson(msg.toString(Charset.forName("UTF-8")), bodyClazz);
        this.messageBody = body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageHeader=" + messageHeader +
                ", messageBody=" + messageBody +
                '}';
    }
}
