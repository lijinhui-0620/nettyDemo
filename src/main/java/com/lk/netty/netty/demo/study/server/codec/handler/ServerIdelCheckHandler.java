package com.lk.netty.netty.demo.study.server.codec.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auther: kim
 * @create: 2020-03-06 11:32
 * @description:
 */
public class ServerIdelCheckHandler extends IdleStateHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ServerIdelCheckHandler() {
        super(10, 0, 0);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        if (evt == IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT) {
            ctx.close();
            return;
        }
        super.channelIdle(ctx, evt);
    }
}
