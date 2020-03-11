package com.lk.netty.netty.demo.study.server.codec.handler;

import com.lk.netty.netty.demo.study.common.Operation;
import com.lk.netty.netty.demo.study.common.RequestMessage;
import com.lk.netty.netty.demo.study.common.auth.AuthOperation;
import com.lk.netty.netty.demo.study.common.auth.AuthOperationResult;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auther: kim
 * @create: 2020-03-06 12:14
 * @description:
 */
@ChannelHandler.Sharable
public class AuthHandler extends SimpleChannelInboundHandler<RequestMessage> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage msg) throws Exception {
        try {
            Operation messageBody = msg.getMessageBody();
            if (messageBody instanceof AuthOperation) {
                AuthOperation operation = (AuthOperation) messageBody;
                AuthOperationResult execute = (AuthOperationResult) operation.execute();
                if (execute.isPassAuth()) {
                    logger.info("pass auth");
                } else {
                    logger.error("fail auth");
                    ctx.close();
                }
            } else {
                logger.info("expect first msg is auth");
                ctx.close();
                ByteBuf buffer = ctx.channel().alloc().buffer();
            }
        } catch (Exception e) {
            logger.error("auth execption", e);
            ctx.close();
        } finally {
            ctx.pipeline().remove(this);
        }
    }
}
