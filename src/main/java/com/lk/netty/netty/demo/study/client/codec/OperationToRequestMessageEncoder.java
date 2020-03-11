package com.lk.netty.netty.demo.study.client.codec;

import com.lk.netty.netty.demo.study.common.Operation;
import com.lk.netty.netty.demo.study.common.RequestMessage;
import com.lk.netty.netty.demo.study.util.IdUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @auther: kim
 * @create: 2020-03-05 12:07
 * @description:
 */
public class OperationToRequestMessageEncoder extends MessageToMessageEncoder<Operation> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Operation msg, List<Object> out) throws Exception {
        RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), msg);
        out.add(requestMessage);
    }
}
