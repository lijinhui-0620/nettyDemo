package com.lk.netty.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @auther: kim
 * @create: 2020-02-20 10:01
 * @description:
 */
public class ChannelDemo03 {

    public static void main(String[] args) throws FileNotFoundException, Exception {
        FileInputStream fileInputStream = new FileInputStream("e:\\file01.txt");
        FileChannel channel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("e:\\file02.txt");
        System.out.println(channel.size());
        FileChannel out = fileOutputStream.getChannel();
        channel.transferTo(0, channel.size(), out);


        fileInputStream.close();
        fileOutputStream.close();
    }
}
