package com.lk.netty.netty.demo.study.client.codec;

import com.lk.netty.netty.demo.study.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @auther: kim
 * @create: 2020-03-05 11:40
 * @description:
 */
public class OrderProtocalDecode extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.decode(msg);
        out.add(responseMessage);
    }
}
