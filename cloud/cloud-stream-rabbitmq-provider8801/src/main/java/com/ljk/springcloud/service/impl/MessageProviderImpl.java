package com.ljk.springcloud.service.impl;

import com.ljk.springcloud.service.IMessageProvider;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

/**
 * @auther: kim
 * @create: 2020-05-21 11:15
 * @description:
 */
@EnableBinding(Source.class)
public class MessageProviderImpl implements IMessageProvider {

    @Autowired
    private MessageChannel output;

    @Override
    public void send() {
        String s = RandomStringUtils.randomAlphabetic(32);
        output.send(MessageBuilder.withPayload(s).build());
        System.out.println("******serial:" + s);
    }
}
