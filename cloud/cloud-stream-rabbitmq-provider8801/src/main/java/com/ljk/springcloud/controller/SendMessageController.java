package com.ljk.springcloud.controller;

import com.ljk.springcloud.service.IMessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: kim
 * @create: 2020-05-21 11:22
 * @description:
 */
@RestController
public class SendMessageController {
    @Autowired
    private IMessageProvider messageProvider;

    @GetMapping("/send")
    public String send() {
        messageProvider.send();
        return "发送完成";
    }
}
