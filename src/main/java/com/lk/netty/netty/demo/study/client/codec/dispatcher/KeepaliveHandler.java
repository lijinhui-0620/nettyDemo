package com.lk.netty.netty.demo.study.client.codec.dispatcher;

import com.lk.netty.netty.demo.study.common.RequestMessage;
import com.lk.netty.netty.demo.study.common.keepalive.KeepaliveOperation;
import com.lk.netty.netty.demo.study.util.IdUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @auther: kim
 * @create: 2020-03-06 11:52
 * @description:
 */
public class KeepaliveHandler extends ChannelDuplexHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            if (evt == IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT) {
                RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), new KeepaliveOperation());
                ctx.writeAndFlush(requestMessage);
                return;
            }
        }
        super.userEventTriggered(ctx, evt);
    }
}
