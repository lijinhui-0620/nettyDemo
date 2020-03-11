package com.lk.netty.nio;

import java.nio.IntBuffer;

/**
 * @auther: kim
 * @create: 2020-02-17 11:48
 * @description:buffer的基本使用
 */
public class BasicBuffer {
    public static void main(String[] args) {
        IntBuffer allocate = IntBuffer.allocate(5);
        allocate.flip();

        int a =5;
        int b = -6;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println(b);
        System.out.println(a);
    }
}
