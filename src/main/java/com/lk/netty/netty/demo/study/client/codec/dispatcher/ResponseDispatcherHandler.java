package com.lk.netty.netty.demo.study.client.codec.dispatcher;

import com.lk.netty.netty.demo.study.common.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @auther: kim
 * @create: 2020-03-05 12:21
 * @description:
 */
public class ResponseDispatcherHandler extends SimpleChannelInboundHandler<ResponseMessage> {

    private RequestPendingCenter requestPendingCentor;

    public ResponseDispatcherHandler(RequestPendingCenter requestPendingCentor) {
        this.requestPendingCentor = requestPendingCentor;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseMessage responseMessage) throws Exception {
        requestPendingCentor.set(responseMessage.getMessageHeader().getStreamId(), responseMessage.getMessageBody());

    }
}
