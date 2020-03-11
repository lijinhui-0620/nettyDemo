package com.lk.netty.nio;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * @auther: kim
 * @create: 2020-02-21 09:52
 * @description:
 */
public class SeletorDemo01 {
    public static void main(String[] args) throws IOException {
        Selector open = Selector.open();
    }
}
