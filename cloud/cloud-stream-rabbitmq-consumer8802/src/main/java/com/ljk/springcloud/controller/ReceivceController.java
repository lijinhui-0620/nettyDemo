package com.ljk.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: kim
 * @create: 2020-05-21 12:01
 * @description:
 */
@RestController
@EnableBinding(Sink.class)
public class ReceivceController {

    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)
    public void receive(Message<String> message) {
        System.out.println("*******serverPort:" + serverPort + ",reveive msg:" + message.getPayload());
    }
}
