package com.lk.netty.netty.demo.study.server.codec.handler;

import com.lk.netty.netty.demo.study.common.Operation;
import com.lk.netty.netty.demo.study.common.OperationResult;
import com.lk.netty.netty.demo.study.common.ResponseMessage;
import com.lk.netty.netty.demo.study.common.RequestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @auther: kim
 * @create: 2020-03-05 11:42
 * @description:
 */
public class OrderServerProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage msg) throws Exception {
        Operation messageBody = msg.getMessageBody();
        //业务处理
        OperationResult execute = messageBody.execute();
        //构建响应数据
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageBody(execute);
        responseMessage.setMessageHeader(msg.getMessageHeader());
        ctx.writeAndFlush(responseMessage);
    }
}
