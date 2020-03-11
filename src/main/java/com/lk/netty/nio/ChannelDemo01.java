package com.lk.netty.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @auther: kim
 * @create: 2020-02-20 10:01
 * @description:
 */
public class ChannelDemo01 {

    public static void main(String[] args) throws FileNotFoundException, Exception {
        FileChannel channel = new FileOutputStream("e:\\file01.txt").getChannel();
        ByteBuffer allocate = ByteBuffer.allocate(256);
        allocate.put("hello,nio channle".getBytes("utf-8"));
        allocate.flip();
        channel.write(allocate);
        channel.close();
    }
}
