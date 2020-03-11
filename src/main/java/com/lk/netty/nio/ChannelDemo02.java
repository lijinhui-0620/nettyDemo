package com.lk.netty.nio;

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
public class ChannelDemo02 {

    public static void main(String[] args) throws FileNotFoundException, Exception {
        FileInputStream fileInputStream = new FileInputStream("e:\\file01.txt");
        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(256);
        while (channel.read(byteBuffer) != -1) {
            byteBuffer.flip();
           System.out.println(new String(byteBuffer.array()));

            byteBuffer.clear();
        }

        fileInputStream.close();
    }
}
