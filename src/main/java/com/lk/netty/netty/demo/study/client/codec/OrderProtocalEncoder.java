package com.lk.netty.netty.demo.study.client.codec;

import com.lk.netty.netty.demo.study.common.RequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @auther: kim
 * @create: 2020-03-05 11:45
 * @description: RequestMessage -> ByteBuf
 */
public class OrderProtocalEncoder extends MessageToMessageEncoder<RequestMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RequestMessage msg, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        msg.encode(buffer);
        out.add(buffer);
    }
}
